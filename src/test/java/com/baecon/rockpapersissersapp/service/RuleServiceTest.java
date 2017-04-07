package com.baecon.rockpapersissersapp.service;

import static org.junit.Assert.*;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.RoundResult;
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

}
