package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.Stats;
import com.baecon.rockpapersissersapp.model.User;
import com.baecon.rockpapersissersapp.repository.StatsRepository;
import com.baecon.rockpapersissersapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class StatsServiceTest {

    private StatsService statsService = new StatsService();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatsRepository statsRepository;

    private User testUser1;
    private User testUser2;

    @Before
    public void setUp() {
        statsService.setStatsRepository(statsRepository);
        RuleService ruleService = new RuleService();
        ruleService.init();
        statsService.setRuleService(ruleService);
        testUser1 = generateUser("user1");
        testUser2 = generateUser("user2");
        statsService.createStatsForUser(testUser1);
        statsService.createStatsForUser(testUser2);
    }

    @Test
    public void testCreateStats() {
        Stats stats = statsRepository.findByUser(testUser1);
        assertTrue(stats != null);
        assertTrue(stats.getUser().equals(testUser1));
        assertStats(stats, 0,0,0,0,0,0,0,0,0);
    }

    @Test
    public void testUpdateStats() {
        // DRAWN tests
        statsService.updateStats(generateTestGame(testUser1, Figure.PAPER, testUser2, Figure.PAPER));
        assertStats(statsRepository.findByUser(testUser1), 1,0,0,1,0,0,0,0,0);
        assertStats(statsRepository.findByUser(testUser2), 1,0,0,1,0,0,0,0,0);
        statsService.updateStats(generateTestGame(testUser1, Figure.ROCK, testUser2, Figure.ROCK));
        assertStats(statsRepository.findByUser(testUser1), 2,0,0,1,1,0,0,0,0);
        assertStats(statsRepository.findByUser(testUser2), 2,0,0,1,1,0,0,0,0);
        statsService.updateStats(generateTestGame(testUser1, Figure.SCISSOR, testUser2, Figure.SCISSOR));
        assertStats(statsRepository.findByUser(testUser1), 3,0,0,1,1,1,0,0,0);
        assertStats(statsRepository.findByUser(testUser2), 3,0,0,1,1,1,0,0,0);

        // WIN tests
        statsService.updateStats(generateTestGame(testUser1, Figure.PAPER, testUser2, Figure.ROCK));
        assertStats(statsRepository.findByUser(testUser1), 3,0,1,2,1,1,1,0,0);
        assertStats(statsRepository.findByUser(testUser2), 3,1,0,1,2,1,0,0,0);
        statsService.updateStats(generateTestGame(testUser1, Figure.ROCK, testUser2, Figure.SCISSOR));
        assertStats(statsRepository.findByUser(testUser1), 3,0,2,2,2,1,1,1,0);
        assertStats(statsRepository.findByUser(testUser2), 3,2,0,1,2,2,0,0,0);
        statsService.updateStats(generateTestGame(testUser1, Figure.SCISSOR, testUser2, Figure.PAPER));
        assertStats(statsRepository.findByUser(testUser1), 3,0,3,2,2,2,1,1,1);
        assertStats(statsRepository.findByUser(testUser2), 3,3,0,2,2,2,0,0,0);

        // LOOSE tests
        statsService.updateStats(generateTestGame(testUser1, Figure.PAPER, testUser2, Figure.SCISSOR));
        assertStats(statsRepository.findByUser(testUser1), 3,1,3,3,2,2,1,1,1);
        assertStats(statsRepository.findByUser(testUser2), 3,3,1,2,2,3,0,0,1);
        statsService.updateStats(generateTestGame(testUser1, Figure.ROCK, testUser2, Figure.PAPER));
        assertStats(statsRepository.findByUser(testUser1), 3,2,3,3,3,2,1,1,1);
        assertStats(statsRepository.findByUser(testUser2), 3,3,2,3,2,3,1,0,1);
        statsService.updateStats(generateTestGame(testUser1, Figure.SCISSOR, testUser2, Figure.ROCK));
        assertStats(statsRepository.findByUser(testUser1), 3,3,3,3,3,3,1,1,1);
        assertStats(statsRepository.findByUser(testUser2), 3,3,3,3,3,3,1,1,1);
    }

    private void assertStats(Stats stats, long drawns, long losses, long wins,
                             long paperCount, long rockCount, long scissorCount,
                             long paperWinCount, long rockWinCount, long scissorWinCount) {
        assertTrue(stats.getDrawns() == drawns);
        assertTrue(stats.getLosses() == losses);
        assertTrue(stats.getWins() == wins);
        assertTrue(stats.getPaperCount() == paperCount);
        assertTrue(stats.getRockCount() == rockCount);
        assertTrue(stats.getScissorCount() == scissorCount);
        assertTrue(stats.getPaperWinCount() == paperWinCount);
        assertTrue(stats.getRockWinCount() == rockWinCount);
        assertTrue(stats.getScissorWinCount() == scissorWinCount);
        assertTrue(stats.getId() != null);
    }

    private User generateUser(String name) {
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    private Game generateTestGame(User user1, Figure fig1, User user2, Figure fig2) {
        Game game = new Game();
        game.setFirstUser(user1);
        game.setFirstFigure(fig1);
        game.setSecondUser(user2);
        game.setSecondFigure(fig2);
        return game;
    }

}
