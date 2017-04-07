package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.model.*;
import com.baecon.rockpapersissersapp.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private RuleService ruleService;

    public void createStatsForUser(User user) {
        Stats stats = new Stats();
        stats.setUser(user);
        statsRepository.save(stats);
    }

    public Stats loadStats(User user) {
        return statsRepository.findByUser(user);
    }

    public void updateStats(Game game) {
        User user1 = game.getFirstUser();
        Figure figure1 = game.getFirstFigure();
        Stats stats1 = statsRepository.findByUser(user1);

        User user2 = game.getSecondUser();
        Figure figure2 = game.getSecondFigure();
        Stats stats2 = statsRepository.findByUser(user2);

        RoundResult roundResult1 = ruleService.getRoundResult(figure1, figure2);
        RoundResult roundResult2 = ruleService.getRoundResult(figure2, figure1);

        updateGameResultStats(stats1, roundResult1);
        updateGameResultStats(stats2, roundResult2);

        updateFigureStats(stats1, figure1, roundResult1);
        updateFigureStats(stats2, figure2, roundResult2);

        statsRepository.save(stats1);
        statsRepository.save(stats2);
    }

    private void updateGameResultStats(Stats stats, RoundResult roundResult) {
        switch (roundResult) {
            case DRAWN:
                stats.setDrawns(stats.getDrawns() + 1);
                break;
            case WIN:
                stats.setWins(stats.getWins() + 1);
                break;
            case LOOSE:
                stats.setLosses(stats.getLosses() + 1);
                break;
        }
    }

    private void updateFigureStats(Stats stats, Figure figure, RoundResult roundResult) {
        switch (figure) {
            case SCISSOR:
                stats.setScissorCount(stats.getScissorCount() + 1);
                if (roundResult.WIN.equals(roundResult)) {
                    stats.setScissorWinCount(stats.getScissorWinCount() + 1);
                }
                break;
            case ROCK:
                stats.setRockCount(stats.getRockCount() + 1);
                if (roundResult.WIN.equals(roundResult)) {
                    stats.setRockWinCount(stats.getRockWinCount() + 1);
                }
                break;
            case PAPER:
                stats.setPaperCount(stats.getPaperCount() + 1);
                if (roundResult.WIN.equals(roundResult)) {
                    stats.setPaperWinCount(stats.getPaperWinCount() + 1);
                }
                break;
        }
    }

    public void setStatsRepository(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }
}
