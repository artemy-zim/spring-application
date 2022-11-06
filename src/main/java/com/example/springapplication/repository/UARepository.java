package com.example.springapplication.repository;


import com.example.springapplication.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UARepository extends JpaRepository<UserAction, Long> {
}
