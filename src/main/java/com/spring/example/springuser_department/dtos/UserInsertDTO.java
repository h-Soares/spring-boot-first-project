package com.spring.example.springuser_department.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

//to use in @RequestBody
public class UserInsertDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String password;
    private UUID departmentId;

    public UserInsertDTO() {
    }

    public UserInsertDTO(String name, String email, String password, UUID departmentId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }
}