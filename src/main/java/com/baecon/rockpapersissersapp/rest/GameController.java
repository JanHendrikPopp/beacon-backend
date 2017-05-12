package com.baecon.rockpapersissersapp.rest;

import com.baecon.rockpapersissersapp.exceptions.DetermineRoundResultException;
import com.baecon.rockpapersissersapp.exceptions.GameNotFoundException;
import com.baecon.rockpapersissersapp.exceptions.UserNotFoundException;
import com.baecon.rockpapersissersapp.model.*;
import com.baecon.rockpapersissersapp.service.GameService;
import com.baecon.rockpapersissersapp.service.RuleService;
import com.baecon.rockpapersissersapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.baecon.rockpapersissersapp.util.ApiConstants.*;

@RestController
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = LOAD_GAME, method = RequestMethod.GET)
    public Game loadGame(@PathVariable("gameId") long gameId) throws GameNotFoundException {
        Game game = gameService.loadGame(gameId);
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        return game;
    }

    @RequestMapping(value = NEW_GAME, method = RequestMethod.GET)
    public Game loadNewGameForTv() {
        return gameService.loadNewGameForTv();
    }

    @RequestMapping(value = MOVE, method = RequestMethod.POST)
    public Game makeMove(@RequestParam("beaconId") String beaconId,
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
    public GameResult getRoundResult(@PathVariable("gameId") long gameId,
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
        Figure figure;
        if (game.getFirstUser().equals(user)) {
            figure = game.getFirstFigure();
        } else {
            figure = game.getSecondFigure();
        }

        return new GameResult(figure, ruleService.getRoundResult(game, user));
    }

    @RequestMapping(value = ALL_GAMES, method = RequestMethod.GET)
    public List<GameResult> getAllGames(@PathVariable("playerId") long playerId) throws UserNotFoundException {
        User user = userService.loadUser(playerId);
        if (user == null) {
            throw new UserNotFoundException(playerId);
        }

        List<GameResult> results = new ArrayList<>();

        for (Game game : gameService.getAllGames(user)) {
            Figure figure;
            RoundResult result;
            if (game.getFirstUser().equals(user)) {
                figure = game.getFirstFigure();
                result = ruleService.getRoundResult(game.getFirstFigure(), game.getSecondFigure());
            } else {
                figure = game.getSecondFigure();
                result = ruleService.getRoundResult(game.getSecondFigure(), game.getFirstFigure());
            }

            results.add(new GameResult(figure, result));
        }

        return results;
    }

}
