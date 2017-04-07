package com.baecon.rockpapersissersapp.service;

import com.baecon.rockpapersissersapp.exceptions.DetermineRoundResultException;
import com.baecon.rockpapersissersapp.model.Figure;
import com.baecon.rockpapersissersapp.model.Game;
import com.baecon.rockpapersissersapp.model.RoundResult;
import com.baecon.rockpapersissersapp.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class RuleService {

    /* The game rules */
    private HashMap<Figure, List<Figure>> rules;

    public RoundResult getRoundResult(Figure fig1, Figure fig2) {
        return determineRoundResult(fig1, fig2);
    }

    public RoundResult getRoundResult(Game game, User user) throws DetermineRoundResultException {
        if (!user.equals(game.getFirstUser()) && !user.equals(game.getSecondUser())) {
            throw new DetermineRoundResultException(user.getId(),
                                                    game.getId(),
                                                    "The given user never played the given game.");
        } else if (game.getFirstUser() == null || game.getSecondUser() == null) {
            return RoundResult.WAITING;
        } else if (game.getFirstUser().equals(user)) {
            return determineRoundResult(game.getFirstFigure(), game.getSecondFigure());
        }

        return determineRoundResult(game.getSecondFigure(), game.getFirstFigure());
    }

    private RoundResult determineRoundResult(Figure fig1, Figure fig2) {
        List<Figure> beats = this.rules.get(fig1);
        if (!beats.isEmpty()) {
            if (beats.contains(fig2)) {
                return RoundResult.WIN;
            }
        }

        List<Figure> loose = this.rules.get(fig2);
        if (!loose.isEmpty()) {
            if (loose.contains(fig1)) {
                return RoundResult.LOOSE;
            }
        }

        return RoundResult.DRAWN;
    }

    @PostConstruct
    public void init() {
        this.rules = new HashMap<Figure, List<Figure>>();
        this.rules.put(Figure.PAPER, Arrays.asList((new Figure[]{Figure.ROCK})));
        this.rules.put(Figure.ROCK, Arrays.asList((new Figure[]{Figure.SCISSOR})));
        this.rules.put(Figure.SCISSOR, Arrays.asList((new Figure[]{Figure.PAPER})));
    }
}
