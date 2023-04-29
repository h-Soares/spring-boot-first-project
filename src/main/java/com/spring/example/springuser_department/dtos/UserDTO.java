package com.spring.example.springuser_department.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.example.springuser_department.entities.User;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID ID;
    private String name;
    private String email;
    @JsonProperty("Department")
    private DepartmentDTO departmentDTO;

    public UserDTO() {
    }

    public UserDTO(UUID ID, String name, String email, DepartmentDTO departmentDTO) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.departmentDTO = departmentDTO;
    }

    public UserDTO(User user) {
        this.ID = user.getID();
        this.name = user.getName();
        this.email = user.getEmail();
        this.departmentDTO = new DepartmentDTO(user.getDepartment());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }
}