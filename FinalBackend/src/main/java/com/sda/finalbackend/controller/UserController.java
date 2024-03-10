package com.sda.finalbackend.controller;

import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import com.sda.finalbackend.errors.UserNotFoundException;
import com.sda.finalbackend.service.UserService;
import com.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        try {

            User userDb = this.userService.createUser(user);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("User saved with success.")
                    .data(userDb)
                    .build();

            return ResponseEntity.ok(response);

        } catch (InvalidEmailOrUsernameException invalidEmailOrUsernameException) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(invalidEmailOrUsernameException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }catch (Throwable throwable){

            ApiResponse response = new ApiResponse.Builder()
                    .status(500)
                    .message(throwable.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        }
    }

    @PatchMapping("/user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user) {
        try {

            User userDb = this.userService.updateUser(user);

            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("User updated with success.")
                    .data(userDb)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException userNotFoundException) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(userNotFoundException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        } catch (InvalidEmailOrUsernameException invalidEmailOrUsernameException) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(401)
                    .message(invalidEmailOrUsernameException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(response);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Integer id) {

        try {

            userService.deleteUser(id);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("User deleted with success.")
                    .data(null)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException userNotFoundException) {

            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(userNotFoundException.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);

        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {

        List<User> users = userService.findAll();

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Users list")
                .data(users)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/username")
    public ResponseEntity<ApiResponse> getByUsername(@RequestParam(name="username") String username) {

        try {

            User userDB = userService.findByUsername(username);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Found by username")
                    .data(userDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }

    }

    @GetMapping("/user/email")
    public ResponseEntity<ApiResponse> getByEmail(@RequestParam(name="email") String email) {

        try {

            User userDB = userService.findByEmail(email);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Found by email")
                    .data(userDB)
                    .build();

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }

    }
}
