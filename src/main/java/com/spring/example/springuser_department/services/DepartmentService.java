package com.spring.example.springuser_department.services;

import com.spring.example.springuser_department.dtos.DepartmentDTO;
import com.spring.example.springuser_department.dtos.DepartmentInsertDTO;
import com.spring.example.springuser_department.entities.Department;
import com.spring.example.springuser_department.repositories.DepartmentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Page<DepartmentDTO> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(DepartmentDTO::new);
    }

    public DepartmentDTO findByID(String uuid) {
        Optional<Department> department = departmentRepository.findById(UUID.fromString(uuid));
        if(department.isEmpty())
            throw new EntityNotFoundException("Department not found");
        return new DepartmentDTO(department.get());
    }

    @Transactional
    public DepartmentDTO insert(DepartmentInsertDTO departmentInsertDTO) {
        if(departmentRepository.existsByName(departmentInsertDTO.getName()))
            throw new EntityExistsException("Name already exists");

        Department department = departmentInsertDTOToDepartment(departmentInsertDTO);
        department = departmentRepository.save(department);
        return new DepartmentDTO(department);
    }

    @Transactional
    public void deleteById(String uuid) {
        Optional<Department> department = departmentRepository.findById(UUID.fromString(uuid));
        if(department.isEmpty())
            throw new EntityNotFoundException("Department not found");
        departmentRepository.delete(department.get());
    }

    @Transactional
    public DepartmentDTO updateById(String uuid, DepartmentInsertDTO departmentInsertDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(UUID.fromString(uuid));
        if(optionalDepartment.isEmpty())
            throw new EntityNotFoundException("Department not found");

        Department department = optionalDepartment.get();
        if(!departmentInsertDTO.getName().equals(department.getName()) && departmentRepository.
                                                       existsByName(departmentInsertDTO.getName()))
            throw new EntityExistsException("Name already exists");
        updateDepartment(department, departmentInsertDTO);
        department = departmentRepository.save(department);

        return new DepartmentDTO(department);
    }

    public boolean existsByUUID(UUID uuid) {
        return departmentRepository.existsById(uuid);
    }

    private Department departmentInsertDTOToDepartment(DepartmentInsertDTO departmentInsertDTO) {
        Department department = new Department();
        department.setName(departmentInsertDTO.getName());
        return department;
    }

    private void updateDepartment(Department department, DepartmentInsertDTO departmentInsertDTO) {
        department.setName(departmentInsertDTO.getName());
    }
}