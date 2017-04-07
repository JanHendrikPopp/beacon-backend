package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Stats;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.StatsRepository;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    private UserService userService = new UserService();

    private StatsService statsService = new StatsService();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatsRepository statsRepository;


    @Before
    public void setUp() {
        statsService.setStatsRepository(statsRepository);
        userService.setUserRepository(userRepository);
        userService.setStatsService(statsService);
    }

    @Test
    public void userRegistration() {
        User user = userService.registerUser("TestName");
        assertTrue(user.getId() != null);
        Long id = user.getId();
        User dbUser = userRepository.findOne(id);
        Stats stats = statsRepository.findByUser(dbUser);
        assertTrue(dbUser != null);
        assertTrue(user.getId().equals(dbUser.getId()));
        assertTrue(user.getName().equals(dbUser.getName()));
        assertTrue(stats != null);
    }

}
