package com.spring.example.springuser_department.dtos;

import java.io.Serial;
import java.io.Serializable;

public class DepartmentInsertDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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