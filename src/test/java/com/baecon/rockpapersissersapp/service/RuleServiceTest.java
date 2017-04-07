package com.baecon.rockpapersissersapp.service;

import static org.junit.Assert.*;

import com.baecon.rockpapersissersapp.exceptions.DetermineRoundResultException;
import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.RoundResult;
import com.baecon.rockpapersissersapp.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleServiceTest {

	@Autowired
	private RuleService ruleService;
	
	@Test
	public void testRoundResults() {
		assertTrue(RoundResult.WIN.equals(ruleService.getRoundResult(Figure.PAPER, Figure.ROCK)));
		assertTrue(RoundResult.LOOSE.equals(ruleService.getRoundResult(Figure.PAPER, Figure.SCISSOR)));
		assertTrue(RoundResult.DRAWN.equals(ruleService.getRoundResult(Figure.PAPER, Figure.PAPER)));
		
		assertTrue(RoundResult.WIN.equals(ruleService.getRoundResult(Figure.ROCK, Figure.SCISSOR)));
		assertTrue(RoundResult.LOOSE.equals(ruleService.getRoundResult(Figure.ROCK, Figure.PAPER)));
		assertTrue(RoundResult.DRAWN.equals(ruleService.getRoundResult(Figure.ROCK, Figure.ROCK)));
		
		assertTrue(RoundResult.WIN.equals(ruleService.getRoundResult(Figure.SCISSOR, Figure.PAPER)));
		assertTrue(RoundResult.LOOSE.equals(ruleService.getRoundResult(Figure.SCISSOR, Figure.ROCK)));
		assertTrue(RoundResult.DRAWN.equals(ruleService.getRoundResult(Figure.SCISSOR, Figure.SCISSOR)));
	}

	@Test(expected=DetermineRoundResultException.class)
	public void testPlayerNotPlayedGame() throws DetermineRoundResultException {
		User user1 = generateTestUser(1L, "user1");
		User user2 = generateTestUser(2L, "user2");
		Game game = generateTestGame(3L, user1, Figure.PAPER, null, null);
		ruleService.getRoundResult(game, user2);
	}

	@Test
	public void testWaiting() throws DetermineRoundResultException {
		User user1 = generateTestUser(1L, "user1");
		Game game = generateTestGame(3L, user1, Figure.PAPER, null, null);
		RoundResult roundResult = ruleService.getRoundResult(game, user1);
		assertTrue(roundResult.equals(RoundResult.WAITING));
	}

	@Test
	public void testGameResult() throws DetermineRoundResultException {
		User user1 = generateTestUser(1L, "user1");
		Figure fig1 = Figure.PAPER;
		User user2 = generateTestUser(2L, "user2");
		Figure fig2 = Figure.SCISSOR;
		Game game = generateTestGame(3L, user1, fig1, user2, fig2);

		assertTrue(ruleService.getRoundResult(game, user1).equals(RoundResult.LOOSE));
		assertTrue(ruleService.getRoundResult(game, user2).equals(RoundResult.WIN));
	}


	private User generateTestUser(long id, String name) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		return user;
	}

	private Game generateTestGame(long id, User user1, Figure fig1, User user2, Figure fig2) {
		Game game = new Game();
		game.setId(id);
		game.setFirstUser(user1);
		game.setFirstFigure(fig1);
		game.setSecondUser(user2);
		game.setSecondFigure(fig2);
		return game;
	}

}
