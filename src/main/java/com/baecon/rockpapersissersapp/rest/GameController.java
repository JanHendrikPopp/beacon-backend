package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.exceptions.DetermineRoundResultException;
import com.baecon.rockpapersissersapp.exceptions.GameNotFoundException;
import com.baecon.rockpapersissersapp.exceptions.UserNotFoundException;
import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.RoundResult;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.service.GameService;
import com.baecon.rockpapersissersapp.service.RuleService;
import com.baecon.rockpapersissersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.baecon.rockpapersissersapp.util.ApiConstants.GAME;
import static com.baecon.rockpapersissersapp.util.ApiConstants.MOVE;
import static com.baecon.rockpapersissersapp.util.ApiConstants.REGISTRATION;

@RestController
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = MOVE, method = RequestMethod.POST)
    public Game makeMove(@RequestParam("beaconId") long beaconId,
                         @RequestParam("playerId") long playerId,
                         @RequestParam("option") Figure option)
            throws UserNotFoundException {
        User user = userService.loadUser(playerId);
        if (user == null) {
            throw new UserNotFoundException(playerId);
        }
        return gameService.makeMove(user, option);
    }

    @RequestMapping(value = GAME, method = RequestMethod.GET)
    public RoundResult getRoundResult(@PathVariable("gameId") long gameId,
                                      @PathVariable("playerId") long playerId)
            throws DetermineRoundResultException, GameNotFoundException, UserNotFoundException {
        Game game = gameService.loadGame(gameId);
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        User user = userService.loadUser(playerId);
        if (user == null) {
            throw new UserNotFoundException(playerId);
        }

        return ruleService.getRoundResult(game, user);
    }

}
