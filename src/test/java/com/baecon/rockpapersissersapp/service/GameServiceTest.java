package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.GameRepository;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


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

    @Before
    public void setUp() {
        userService.setUserRepository(userRepository);
        gameService.setGameRepository(gameRepository);
        firstUser = userService.registerUser("FirstUser");
        secondUser = userService.registerUser("SecondUser");
    }

    @Test
    public void testMakeMove() {
        Game firstMove = gameService.makeMove(firstUser, Figure.PAPER);

        assertThat(firstMove.getId() != null);
        Long id = firstMove.getId();
        Game dbGame = gameRepository.findOne(id);
        assertThat(dbGame != null);
        assertThat(firstMove.getId().equals(dbGame.getId()));
        assertThat(firstMove.getFirstUser().getId().equals(firstUser.getId()));
        assertThat(firstMove.getFirstFigure().equals(Figure.ROCK));

        Game secondMove = gameService.makeMove(firstUser, Figure.PAPER);

        assertThat(secondMove.getId() != null);
        assertThat(secondMove.getId().equals(firstMove.getId()));
        assertThat(secondMove.getSecondUser().getId().equals(secondUser.getId()));
        assertThat(secondMove.getSecondFigure().equals(Figure.ROCK));
    }

}
