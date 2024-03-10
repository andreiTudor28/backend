package com.sda.finalbackend.service;

import com.sda.finalbackend.dto.UserDto;
import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.entity.UserRole;
import com.sda.finalbackend.errors.InvalidCredentialsException;
import com.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import com.sda.finalbackend.errors.UserNotFoundException;
import com.sda.finalbackend.repository.UserRepository;
import com.sda.finalbackend.utils.Utils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto login(String email, String password) throws InvalidCredentialsException {
        Optional<User> userOptional = this.userRepository.getByEmail(email);

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Email inexistent.");
        }

        String passwordFromDB = userOptional.get().getPassword();

        if (Utils.getInstance().checkPassword(password, passwordFromDB)) {

            UserDto userDto = new UserDto();
            userDto.setId(userOptional.get().getId());
            userDto.setEmail(userOptional.get().getEmail());
            userDto.setUsername(userOptional.get().getUsername());
            userDto.setUserRole(userOptional.get().getUserRole());


            return userDto;

        } else {

            throw new InvalidCredentialsException("Invalid password");
        }

    }

    public UserDto register(User user) throws InvalidEmailOrUsernameException, InvalidCredentialsException {

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Email-ul este invalid.");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Username-ul este invalid.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidCredentialsException("Paswword is invalid.");
        }

        Optional<User> userOptional = this.userRepository.getByEmailOrUsername(user.getEmail(), user.getUsername());
        if (userOptional.isPresent()) {
            //inseamna ca in baza de date exista deja un user cu aceste date
            throw new InvalidEmailOrUsernameException("Email sau username existente in baza de date.");
        }

        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(UserRole.USER);

        //Criptam parola folosind libraria BCrypt, parola criptata o salvam in baza de date
        String encryptedPassword = Utils.getInstance().encryptPassword(user.getPassword());
        usr.setPassword(encryptedPassword);

        User userDB = this.userRepository.save(usr);

        UserDto userDto = new UserDto();
        userDto.setId(userDB.getId());
        userDto.setUsername(userDB.getUsername());
        userDto.setEmail(userDB.getEmail());
        userDto.setUserRole(userDB.getUserRole());

        return userDto;
    }


}
