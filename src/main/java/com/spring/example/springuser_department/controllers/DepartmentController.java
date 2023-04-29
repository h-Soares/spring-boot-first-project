package com.spring.example.springuser_department.controllers;

import com.spring.example.springuser_department.dtos.DepartmentDTO;
import com.spring.example.springuser_department.dtos.DepartmentInsertDTO;
import com.spring.example.springuser_department.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> findAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DepartmentDTO> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(departmentService.findByID(uuid));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> insert(@RequestBody DepartmentInsertDTO departmentInsertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).
               body(departmentService.insert(departmentInsertDTO));
    }
}