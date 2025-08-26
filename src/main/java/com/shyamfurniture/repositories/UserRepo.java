package com.shyamfurniture.repositories;

import com.shyamfurniture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {

    //find by name api

   Optional<User> findByEmail(String email);

   //find by email and password

    Optional<User> findByEmailAndPassword(String email,String password);

    //search by keyword api

    List<User> findByNameContaining(String keyword);

}
