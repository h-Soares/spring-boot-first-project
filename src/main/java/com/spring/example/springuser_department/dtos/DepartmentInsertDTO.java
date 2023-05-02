package com.spring.example.springuser_department.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public class DepartmentInsertDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String REGEX = "^[A-Za-z0-9]+(?: [A-Za-z0-9]+)*$";

    @NotBlank(message = "Name can not be null or empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = REGEX, message = "Invalid name")
    private String name;

    public DepartmentInsertDTO() {
    }

    public DepartmentInsertDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}