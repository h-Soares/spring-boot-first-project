package com.spring.example.springuser_department.controllers;

import com.spring.example.springuser_department.dtos.DepartmentDTO;
import com.spring.example.springuser_department.dtos.DepartmentInsertDTO;
import com.spring.example.springuser_department.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Tag(name = "DepartmentController", description = "Department operations")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(description = "Get a paginated list of all departments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping //Alterar o README!
    public ResponseEntity<Page<DepartmentDTO>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(departmentService.findAll(pageable));
    }

    @Operation(description = "Get a department by UUID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Illegal argument"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<DepartmentDTO> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(departmentService.findByID(uuid));
    }

    @Operation(description = "Insert a new department", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "409", description = "Entity already exists")
    })
    @PostMapping
    public ResponseEntity<DepartmentDTO> insert(@RequestBody @Valid DepartmentInsertDTO departmentInsertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).
               body(departmentService.insert(departmentInsertDTO));
    }

    @Operation(description = "Delete a department by UUID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid argument"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable String uuid) {
        departmentService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Update a department by UUID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "409", description = "Entity already exists")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<DepartmentDTO> updateById(@PathVariable String uuid,
           @RequestBody @Valid DepartmentInsertDTO departmentInsertDTO) {
        return ResponseEntity.ok(departmentService.updateById(uuid, departmentInsertDTO));
    }
}