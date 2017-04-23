package com.baecon.rockpapersissersapp.model;

public class GameResult {

    private Figure option;

    private RoundResult result;

    public GameResult() {
    }

    public GameResult(Figure option, RoundResult result) {
        this.option = option;
        this.result = result;
    }

    public Figure getOption() {
        return option;
    }

    public void setOption(Figure option) {
        this.option = option;
    }

    public RoundResult getResult() {
        return result;
    }

    public void setResult(RoundResult result) {
        this.result = result;
    }
}
