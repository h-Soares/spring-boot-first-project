package com.spring.example.springuser_department.controllers;

import com.spring.example.springuser_department.dtos.DepartmentDTO;
import com.spring.example.springuser_department.dtos.DepartmentInsertDTO;
import com.spring.example.springuser_department.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentDTO>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(departmentService.findAll(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DepartmentDTO> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(departmentService.findByID(uuid));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> insert(@RequestBody @Valid DepartmentInsertDTO departmentInsertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).
               body(departmentService.insert(departmentInsertDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable String uuid) {
        departmentService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DepartmentDTO> updateById(@PathVariable String uuid,
           @RequestBody @Valid DepartmentInsertDTO departmentInsertDTO) {
        return ResponseEntity.ok(departmentService.updateById(uuid, departmentInsertDTO));
    }
}