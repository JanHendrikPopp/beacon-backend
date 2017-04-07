package com.baecon.rockpapersissersapp.model;

import javax.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User firstUser;

    @OneToOne
    private  User secondUser;

    @Column
    private Figure firstFigure;

    @Column
    private Figure secondFigure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Figure getFirstFigure() {
        return firstFigure;
    }

    public void setFirstFigure(Figure firstFigure) {
        this.firstFigure = firstFigure;
    }

    public Figure getSecondFigure() {
        return secondFigure;
    }

    public void setSecondFigure(Figure secondFigure) {
        this.secondFigure = secondFigure;
    }
}
