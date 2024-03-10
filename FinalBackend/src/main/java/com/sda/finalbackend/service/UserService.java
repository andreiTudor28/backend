package com.sda.finalbackend.service;

import com.sda.finalbackend.entity.User;
import com.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import com.sda.finalbackend.errors.UserNotFoundException;
import com.sda.finalbackend.repository.UserRepository;
import com.sda.finalbackend.utils.Utils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws InvalidEmailOrUsernameException {

        //TODO: De adaugat un regex pentru adresa de email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Email-ul este invalid.");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Username-ul este invalid.");
        }

        Optional<User> userOptional = this.userRepository.getByEmailOrUsername(user.getEmail(), user.getUsername());
        if (userOptional.isPresent()) {
            //inseamna ca in baza de date exista deja un user cu aceste date
            throw new InvalidEmailOrUsernameException("Email sau username existente in baza de date.");
        }

        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(user.getUserRole());

        //Criptam parola folosind libraria BCrypt, parola criptata o salvam in baza de date
        String encryptedPassword = Utils.getInstance().encryptPassword(user.getPassword());
        usr.setPassword(encryptedPassword);

        return this.userRepository.save(usr);
    }

    public User updateUser(User user) throws UserNotFoundException, InvalidEmailOrUsernameException {

        Optional<User> userOptional = this.userRepository.findById(user.getId());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User-ul nu exista.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Email-ul este invalid.");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidEmailOrUsernameException("Username-ul este invalid.");
        }

        userOptional = this.userRepository.getByUsername(user.getUsername());

        if (userOptional.isPresent()) {

            if (!userOptional.get().getId().equals(user.getId())) {

                throw new InvalidEmailOrUsernameException("Username-ul este deja in baza de date..");
            }
        }

        userOptional = this.userRepository.getByEmail(user.getEmail());

        if (userOptional.isPresent()) {

            if (!userOptional.get().getId().equals(user.getId())) {

                throw new InvalidEmailOrUsernameException("Email-ul este deja in baza de date..");
            }

        }

        String encryptedPassword= Utils.getInstance().encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        return this.userRepository.save(user);
    }

    public void deleteUser(Integer id) throws UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found in database.");
        }

        this.userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Integer id) throws UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found by id.");
        }

        return userOptional.get();
    }

    public User findByEmail(String email) throws UserNotFoundException {

        Optional<User> userOptional = this.userRepository.getByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found by email.");
        }

        return userOptional.get();

    }

    public User findByUsername(String username) throws UserNotFoundException {

        Optional<User> userOptional = this.userRepository.getByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found by username.");
        }

        return userOptional.get();
    }




}
