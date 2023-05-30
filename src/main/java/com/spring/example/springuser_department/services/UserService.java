package com.spring.example.springuser_department.services;

import com.spring.example.springuser_department.dtos.UserDTO;
import com.spring.example.springuser_department.dtos.UserInsertDTO;
import com.spring.example.springuser_department.entities.Department;
import com.spring.example.springuser_department.entities.User;
import com.spring.example.springuser_department.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentService departmentService;

    public UserService(UserRepository userRepository, DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public UserDTO findById(String uuid) {
        try {
            Optional<User> user = userRepository.findById(UUID.fromString(uuid));
            if(user.isEmpty())
                throw new EntityNotFoundException("User not found");
            return new UserDTO(user.get());
        }catch(IllegalArgumentException e) { //this is throws when STRING type (not UUID) is in parameter
            throw new EntityNotFoundException("Illegal user UUID format");
        }
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) {
        if(userRepository.existsByEmail(userInsertDTO.getEmail()))
            throw new EntityExistsException("Email already exists");

        if(!departmentService.existsByUUID(UUID.fromString(userInsertDTO.getDepartmentId())))
            throw new EntityNotFoundException("Department not found");

        User user = userRepository.save(userInsertDTOToUser(userInsertDTO));
        return new UserDTO(user);
    }

    @Transactional
    public void deleteById(String uuid) {
        try {
            Optional<User> user = userRepository.findById(UUID.fromString(uuid));
            if(user.isEmpty())
                throw new EntityNotFoundException("User not found");
            userRepository.delete(user.get());
        }catch(IllegalArgumentException e) {
            throw new EntityNotFoundException("Illegal user UUID format");
        }
    }

    @Transactional
    public UserDTO updateById(String uuid, UserInsertDTO userInsertDTO) {
        try {
            Optional<User> optionalUser = userRepository.findById(UUID.fromString(uuid));
            if(optionalUser.isEmpty())
                throw new EntityNotFoundException("User not found");

            if(!departmentService.existsByUUID(UUID.fromString(userInsertDTO.getDepartmentId())))
                throw new EntityNotFoundException("Department not found");

            User user = optionalUser.get();
            if(!userInsertDTO.getEmail().equals(user.getEmail()) && userRepository.
                                           existsByEmail(userInsertDTO.getEmail()))
                throw new EntityExistsException("Email already exists");
            updateUser(user, userInsertDTO);
            user = userRepository.save(user);

            return new UserDTO(user);
        }catch(IllegalArgumentException e) { //to validate the uuid in API request, not userInsertDTO
            throw new EntityNotFoundException("Illegal user UUID format");
        }
    }

    private User userInsertDTOToUser(UserInsertDTO userInsertDTO) {
        Department department = new Department();
        department.setID(UUID.fromString(userInsertDTO.getDepartmentId())); //searched by JPA

        User user = new User();
        user.setName(userInsertDTO.getName());
        user.setEmail(userInsertDTO.getEmail());
        user.setPassword(encryptPassword(userInsertDTO.getPassword()));
        user.setDepartment(department);

        return user;
    }

    private void updateUser(User user, UserInsertDTO userInsertDTO) {
        Department department = new Department();
        department.setID(UUID.fromString(userInsertDTO.getDepartmentId())); //searched by JPA

        user.setName(userInsertDTO.getName());
        user.setEmail(userInsertDTO.getEmail());
        user.setPassword(encryptPassword(userInsertDTO.getPassword()));
        user.setDepartment(department);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}