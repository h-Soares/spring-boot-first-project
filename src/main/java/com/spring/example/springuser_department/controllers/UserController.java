package com.spring.example.springuser_department.controllers;

import com.spring.example.springuser_department.dtos.UserDTO;
import com.spring.example.springuser_department.dtos.UserInsertDTO;
import com.spring.example.springuser_department.services.UserService;
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

@RestController //Com @Controller não estava funcionando
@RequestMapping("/users")
@Tag(name = "UserController", description = "User operations")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Get a paginated list of all users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @Operation(description = "Get a user by UUID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Illegal argument"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.findById(uuid));
    }

    @Operation(description = "Insert a new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "409", description = "Entity already exists")
    })
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserInsertDTO userInsertDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userInsertDTO));
    }

    @Operation(description = "Delete a user by UUID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid argument"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable String uuid) {
        userService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Update a user by UUID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "409", description = "Entity already exists")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> updateById(@PathVariable String uuid, @RequestBody @Valid
                                              UserInsertDTO userInsertDTO) {
        return ResponseEntity.ok(userService.updateById(uuid, userInsertDTO));
    }
}