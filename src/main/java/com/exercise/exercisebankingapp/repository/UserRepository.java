package com.exercise.exercisebankingapp.repository;

import com.exercise.exercisebankingapp.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {


    @Query("SELECT s FROM MyUser s WHERE s.name = ?1")
    Optional<MyUser> findUserByName(String name);

    @Query("SELECT s FROM MyUser s WHERE s.email = ?1")
    Optional<MyUser> findUserByEmail(String email);

    @Query("SELECT s FROM MyUser s WHERE s.phoneNumber = ?1")
    Optional<MyUser> findUserByPhoneNumber(String phoneNumber);

    @Query("SELECT s FROM MyUser s WHERE s.address = ?1")
    Optional<MyUser> findUserByAddress(String address);

    @Query("SELECT s FROM MyUser s WHERE s.dateOfBirth = ?1")
    Optional<MyUser> findUserByDateOfBirth(String dateOfBirth);

    @Query("SELECT s FROM MyUser s WHERE s.email = ?1")
    Optional<Object> findByEmail(String email);

}
