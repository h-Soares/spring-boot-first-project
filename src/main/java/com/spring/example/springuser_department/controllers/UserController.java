package com.spring.example.springuser_department.controllers;

import com.spring.example.springuser_department.dtos.UserDTO;
import com.spring.example.springuser_department.dtos.UserInsertDTO;
import com.spring.example.springuser_department.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.findById(uuid));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserInsertDTO userInsertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userInsertDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable String uuid) {
        userService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> updateById(@PathVariable String uuid, @RequestBody @Valid
                                              UserInsertDTO userInsertDTO) {
        return ResponseEntity.ok(userService.updateById(uuid, userInsertDTO));
    }
}