package com.example.list_am.repository;

import com.example.list_am.entity.Category;
import com.example.list_am.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
