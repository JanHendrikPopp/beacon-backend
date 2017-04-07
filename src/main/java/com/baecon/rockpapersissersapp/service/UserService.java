package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service providing methods to handle user operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Registers a new User
     *
     * @param name The name of the user
     * @return {@link User}
     */
    public User registerUser(String name) {
        User user = new User();
        user.setName(name);
        user = userRepository.save(user);
        return user;
    }

    public User loadUser(long id) {
        return userRepository.findOne(id);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
