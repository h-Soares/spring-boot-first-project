package com.spring.example.springuser_department.dtos;

import com.spring.example.springuser_department.entities.Department;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class DepartmentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID ID;
    private String name;

    public DepartmentDTO() {
    }

    public DepartmentDTO(UUID ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public DepartmentDTO(Department department) {
        this.ID = department.getID();
        this.name = department.getName();
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}