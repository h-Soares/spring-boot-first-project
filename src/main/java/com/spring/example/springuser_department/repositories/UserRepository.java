package com.spring.example.springuser_department.repositories;

import com.spring.example.springuser_department.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    //without count -> error
    @Query(value = "SELECT user FROM User user JOIN FETCH user.department", countQuery = "SELECT count(user) FROM User user JOIN user.department")
    Page<User> findAll(Pageable pageable);
}