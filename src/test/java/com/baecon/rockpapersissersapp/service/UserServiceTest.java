package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    private UserService userService = new UserService();

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService.setUserRepository(userRepository);
    }

    @Test
    public void userRegistration() {
        User user = userService.registerUser("TestName");
        assertThat(user.getId() != null);
        Long id = user.getId();
        User dbUser = userRepository.findOne(id);
        assertThat(dbUser != null);
        assertThat(user.getId().equals(dbUser.getId()));
        assertThat(user.getName().equals(dbUser.getName()));
    }

}
