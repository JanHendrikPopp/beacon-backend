package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service providing methods to handle operations on games.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StatsService statsService;

    public Game makeMove(User user, Figure figure) {
        List<Game> openGames = gameRepository.findBySecondUserIsNull();
        Game game;
        if (!openGames.isEmpty()) {
            game = openGames.get(0);
            game.setSecondUser(user);
            game.setSecondFigure(figure);
            statsService.updateStats(game);
        } else {
            game = new Game();
            game.setFirstUser(user);
            game.setFirstFigure(figure);
        }
        game = gameRepository.save(game);
        return game;
    }

    public List<Game> getAllGames(User user) {
        List<Game> games = new ArrayList<>();
        games.addAll(gameRepository.findByFirstUserAndSecondUserIsNotNull(user));
        games.addAll(gameRepository.findBySecondUserAndFirstUserIsNotNull(user));
        return games;
    }

    public Game loadGame(long id) {
        return gameRepository.findOne(id);
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void setStatsService(StatsService statsService) {
        this.statsService = statsService;
    }
}
