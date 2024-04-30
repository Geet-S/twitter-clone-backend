package com.example.socialmediaapp.Repository;

import com.example.socialmediaapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String email);


}
