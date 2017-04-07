package com.baecon.rockpapersissersapp.model;

import javax.persistence.*;

@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @Column
    private long drawns;

    @Column
    private long losses;

    @Column
    private long wins;

    @Column
    private long rockCount;

    @Column
    private long rockWinCount;

    @Column
    private long scissorCount;

    @Column
    private long scissorWinCount;

    @Column
    private long paperCount;

    @Column
    private long paperWinCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getDrawns() {
        return drawns;
    }

    public void setDrawns(long drawns) {
        this.drawns = drawns;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getRockCount() {
        return rockCount;
    }

    public void setRockCount(long rockCount) {
        this.rockCount = rockCount;
    }

    public long getRockWinCount() {
        return rockWinCount;
    }

    public void setRockWinCount(long rockWinCount) {
        this.rockWinCount = rockWinCount;
    }

    public long getScissorCount() {
        return scissorCount;
    }

    public void setScissorCount(long scissorCount) {
        this.scissorCount = scissorCount;
    }

    public long getScissorWinCount() {
        return scissorWinCount;
    }

    public void setScissorWinCount(long scissorWinCount) {
        this.scissorWinCount = scissorWinCount;
    }

    public long getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(long paperCount) {
        this.paperCount = paperCount;
    }

    public long getPaperWinCount() {
        return paperWinCount;
    }

    public void setPaperWinCount(long paperWinCount) {
        this.paperWinCount = paperWinCount;
    }
}
