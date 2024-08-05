package com.exercise.exercisebankingapp.repository;

import com.exercise.exercisebankingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT s FROM User s WHERE s.name = ?1")
    Optional<User> findUserByName(String name);

    @Query("SELECT s FROM User s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT s FROM User s WHERE s.phoneNumber = ?1")
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    @Query("SELECT s FROM User s WHERE s.address = ?1")
    Optional<User> findUserByAddress(String address);

    @Query("SELECT s FROM User s WHERE s.dateOfBirth = ?1")
    Optional<User> findUserByDateOfBirth(String dateOfBirth);
}
