package com.devcorp.bank_proj.repository;

import com.devcorp.bank_proj.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
