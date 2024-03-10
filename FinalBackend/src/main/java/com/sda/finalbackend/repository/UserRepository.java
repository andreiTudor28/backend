package com.sda.finalbackend.repository;

import com.sda.finalbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Integer>{
    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    Optional<User> getByEmailOrUsername(String email, String username);

    @Query(value="SELECT * FROM tbl_users",nativeQuery = true)
    List<User> selectAllUsers();
}
