package com.sda.finalbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="tbl_users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
