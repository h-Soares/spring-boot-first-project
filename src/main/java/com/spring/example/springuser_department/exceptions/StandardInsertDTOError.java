package com.spring.example.springuser_department.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class StandardInsertDTOError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private int status;
    private List<String> errors;
    private String path;

    public StandardInsertDTOError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}