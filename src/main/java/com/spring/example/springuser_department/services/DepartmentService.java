package com.spring.example.springuser_department.services;

import com.spring.example.springuser_department.dtos.DepartmentDTO;
import com.spring.example.springuser_department.dtos.DepartmentInsertDTO;
import com.spring.example.springuser_department.entities.Department;
import com.spring.example.springuser_department.repositories.DepartmentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream().map(DepartmentDTO::new).collect(Collectors.toList());
    }

    public DepartmentDTO findByID(String uuid) {
        try {
            Optional<Department> department = departmentRepository.findById(UUID.fromString(uuid));
            if(department.isEmpty())
                throw new EntityNotFoundException("Department not found");
            return new DepartmentDTO(department.get());
        }catch(IllegalArgumentException e) {
            throw new EntityNotFoundException("Illegal department UUID format");
        }
    }

    @Transactional
    public DepartmentDTO insert(DepartmentInsertDTO departmentInsertDTO) {
        if(departmentRepository.existsByName(departmentInsertDTO.getName()))
            throw new EntityExistsException("Name already exists");

        Department department = new Department(departmentInsertDTO.getName());
        department = departmentRepository.save(department);
        return new DepartmentDTO(department);
    }

    @Transactional
    public void deleteById(String uuid) {
        try {
            Optional<Department> department = departmentRepository.findById(UUID.fromString(uuid));
            if(department.isEmpty())
                throw new EntityNotFoundException("Department not found");
            departmentRepository.delete(department.get());
        }catch(IllegalArgumentException e) {
            throw new EntityNotFoundException("Illegal department UUID format");
        }
    }

    @Transactional
    public DepartmentDTO updateById(String uuid, DepartmentInsertDTO departmentInsertDTO) {
        try {
            Optional<Department> optionalDepartment = departmentRepository.findById(UUID.fromString(uuid));
            if(optionalDepartment.isEmpty())
                throw new EntityNotFoundException("Department not found");

            Department department = optionalDepartment.get();
            updateDepartment(department, departmentInsertDTO);
            department = departmentRepository.save(department);

            return new DepartmentDTO(department);
        }catch(IllegalArgumentException e) {
            throw new EntityNotFoundException("Illegal department UUID format");
        }
    }

    private void updateDepartment(Department department, DepartmentInsertDTO departmentInsertDTO) {
        department.setName(departmentInsertDTO.getName());
    }
}