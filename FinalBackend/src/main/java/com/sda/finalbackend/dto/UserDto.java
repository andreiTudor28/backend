package com.sda.finalbackend.dto;

import com.sda.finalbackend.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String username;
    private String email;
    private UserRole userRole;

}
