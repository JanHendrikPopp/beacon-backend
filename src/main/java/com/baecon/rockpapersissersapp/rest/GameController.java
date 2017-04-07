package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.exceptions.UserNotFoundException;
import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.service.GameService;
import com.baecon.rockpapersissersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.baecon.rockpapersissersapp.util.ApiConstants.MOVE;
import static com.baecon.rockpapersissersapp.util.ApiConstants.REGISTRATION;

@RestController
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = MOVE + "{playerId}", method = RequestMethod.POST)
    public Game makeMove(@RequestParam("beaconId") long beaconId,
                         @RequestParam("playerId") long playerId,
                         @RequestParam("option") Figure option) throws UserNotFoundException {
        User user = userService.loadUser(playerId);
        if (user == null) {
            throw new UserNotFoundException(playerId);
        }
        return gameService.makeMove(user, option);
    }

}
