package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.exceptions.UserNotFoundException;
import com.baecon.rockpapersissersapp.model.*;
import com.baecon.rockpapersissersapp.service.StatsService;
import com.baecon.rockpapersissersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.baecon.rockpapersissersapp.util.ApiConstants.STATS;

@RestController
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatsService statsService;

    @RequestMapping(value = STATS, method = RequestMethod.GET)
    public Stats getStats(@PathVariable("playerId") long playerId) throws UserNotFoundException {
        User user = userService.loadUser(playerId);
        if (user == null) {
            throw new UserNotFoundException(playerId);
        }
        return statsService.loadStats(user);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setStatsService(StatsService statsService) {
        this.statsService = statsService;
    }
}
