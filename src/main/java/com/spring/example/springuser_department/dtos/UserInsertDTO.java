package com.spring.example.springuser_department.dtos;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;

public class UserInsertDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String REGEX_NAME = "^(?!.*[#@!0-9])[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)*$";
    private static final String REGEX_PASSWORD =  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()\\-_=+{}\\[\\]|\\\\:" +
                                                 ";\"'<>,.?/`~]{6,}$";
    private static final String REGEX_UUID = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89aAbB]" +
                                             "[a-fA-F0-9]{3}-[a-fA-F0-9]{12}$";

    @NotBlank(message = "Name can not be null or empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 65 characters")
    @Pattern(regexp = REGEX_NAME, message = "Invalid name")
    private String name;

    @NotBlank(message = "Email can not be null or empty")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password can not be null or empty")
    @Pattern(regexp = REGEX_PASSWORD, message =
            "The password must contain at least 6 characters, with at least one number.")
    private String password;

    @NotBlank(message = "UUID can not be null or empty")
    @Pattern(regexp = REGEX_UUID, message = "Illegal department UUID format")
    private String departmentId;

    public UserInsertDTO() {
    }

    public UserInsertDTO(String name, String email, String password, String departmentId) {
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}