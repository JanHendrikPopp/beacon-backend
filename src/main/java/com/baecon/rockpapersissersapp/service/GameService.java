package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.GameRepository;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service providing methods to handle operations on games.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RuleService ruleService;

    public Game makeMove(User user, Figure figure) {
        List<Game> openGames = gameRepository.findBySecondUserIsNull();
        Game game;
        if (!openGames.isEmpty()) {
            game = openGames.get(0);
            game.setSecondUser(user);
            game.setSecondFigure(figure);
        } else {
            game = new Game();
            game.setFirstUser(user);
            game.setFirstFigure(figure);
        }
        game = gameRepository.save(game);
        return game;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }
}
