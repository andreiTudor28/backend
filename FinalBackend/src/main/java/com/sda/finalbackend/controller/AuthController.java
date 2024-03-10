package com.sda.finalbackend.controller;

import com.sda.finalbackend.dto.LoginDto;
import com.sda.finalbackend.dto.UserDto;
import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.errors.InvalidCredentialsException;
import com.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import com.sda.finalbackend.service.AuthService;
import com.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        try {
            UserDto userDto=this.authService.login(loginDto.getEmail(), loginDto.getPassword());
            ApiResponse response=new ApiResponse
                    .Builder()
                    .status(200)
                    .message("Welcome! "+userDto.getUsername())
                    .data(userDto)
                    .build();

            return ResponseEntity.ok(response);

        } catch (InvalidCredentialsException invalidCredentialsException) {

            ApiResponse response=new ApiResponse
                    .Builder()
                    .status(400)
                    .message(invalidCredentialsException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(400).body(response);

        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User userBody) {
        try {
            UserDto userDto=this.authService.register(userBody);
            ApiResponse response=new ApiResponse
                    .Builder()
                    .status(200)
                    .message("Welcome! "+userDto.getUsername())
                    .data(userDto)
                    .build();

            return ResponseEntity.ok(response);

        } catch (InvalidCredentialsException | InvalidEmailOrUsernameException exception) {

            ApiResponse response=new ApiResponse
                    .Builder()
                    .status(400)
                    .message(exception.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(400).body(response);

        }
    }
}
