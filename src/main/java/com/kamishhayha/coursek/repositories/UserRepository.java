package com.kamishhayha.coursek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamishhayha.coursek.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
