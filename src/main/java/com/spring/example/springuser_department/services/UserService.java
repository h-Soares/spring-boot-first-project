package com.spring.example.springuser_department.services;

import com.spring.example.springuser_department.dtos.UserDTO;
import com.spring.example.springuser_department.dtos.UserInsertDTO;
import com.spring.example.springuser_department.entities.Department;
import com.spring.example.springuser_department.entities.User;
import com.spring.example.springuser_department.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService { //MORE TO DO: Validations, Pagination, etc.
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
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

            User user = optionalUser.get();
            updateUser(user, userInsertDTO);
            user = userRepository.save(user);

            return new UserDTO(user);
        }catch(IllegalArgumentException e) {
            throw new EntityNotFoundException("Illegal user UUID format");
        }
    }

    private User userInsertDTOToUser(UserInsertDTO userInsertDTO) {
        Department department = new Department();
        department.setID(UUID.fromString(userInsertDTO.getDepartmentId())); //searched by JPA

        User user = new User();
        user.setName(userInsertDTO.getName());
        user.setEmail(userInsertDTO.getEmail());
        user.setPassword(userInsertDTO.getPassword());
        user.setDepartment(department);

        return user;
    }

    private void updateUser(User user, UserInsertDTO userInsertDTO) {
        Department department = new Department();
        department.setID(UUID.fromString(userInsertDTO.getDepartmentId()));

        user.setName(userInsertDTO.getName());
        user.setEmail(userInsertDTO.getEmail());
        user.setPassword(userInsertDTO.getPassword());
        user.setDepartment(department);
    }
}