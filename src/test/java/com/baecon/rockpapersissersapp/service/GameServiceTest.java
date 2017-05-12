package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.GameRepository;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@DataJpaTest
public class GameServiceTest {

    private UserService userService = new UserService();

    private GameService gameService = new GameService();

    private User firstUser;
    private User secondUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Mock
    private StatsService statsService;

    @Before
    public void setUp() {
        userService.setUserRepository(userRepository);
        userService.setStatsService(statsService);
        gameService.setGameRepository(gameRepository);
        gameService.setStatsService(statsService);
        firstUser = userService.registerUser("FirstUser");
        secondUser = userService.registerUser("SecondUser");
    }

    @Test
    public void testMakeMove() {
        Game firstMove = gameService.makeMove(firstUser, Figure.PAPER);

        assertTrue(firstMove.getId() != null);
        Long id = firstMove.getId();
        Game dbGame = gameRepository.findOne(id);
        assertTrue(dbGame != null);
        assertTrue(firstMove.getId().equals(dbGame.getId()));
        assertTrue(firstMove.getFirstUser().getId().equals(firstUser.getId()));
        assertTrue(firstMove.getFirstFigure().equals(Figure.PAPER));

        Game secondMove = gameService.makeMove(secondUser, Figure.PAPER);

        assertTrue(secondMove.getId() != null);
        assertTrue(secondMove.getId().equals(firstMove.getId()));
        assertTrue(secondMove.getSecondUser().getId().equals(secondUser.getId()));
        assertTrue(secondMove.getSecondFigure().equals(Figure.PAPER));
        verify(statsService, times(1)).updateStats(any());
    }

    @Test
    public void testGettAllGames() {
        gameService.makeMove(firstUser, Figure.PAPER);
        gameService.makeMove(secondUser, Figure.PAPER);

        List<Game> games = gameService.getAllGames(firstUser);
        assertTrue(games.size() == 1);

        games = gameService.getAllGames(secondUser);
        assertTrue(games.size() == 1);
    }

    @Test
    public void testLoadNewGameForTv() {
        Game game = gameService.loadNewGameForTv();
        assertNull(game);
        Game move = gameService.makeMove(firstUser, Figure.PAPER);
        assertFalse(move.isDisplayed());
        game = gameService.loadNewGameForTv();
        assertNotNull(game);
        assertEquals(move.getId(), game.getId());
        assertTrue(game.isDisplayed());
    }

}
