package com.oracle.scoreboard.model;

public final class Ball {

    private int ballScore = -1;

    Ball() {
    }

    public void addBallScore(int score) {
        this.ballScore = score;
    }

    public int getBallScore() {
        return ballScore;
    }
}
