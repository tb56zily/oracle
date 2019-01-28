package com.oracle.scoreboard.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BowlingFrame {

//    private static final AtomicInteger frameNumberGenerator = new AtomicInteger(1);

    private int frameNumber;

    private int frameScore;

    private List<Ball> frameBalls;

    public BowlingFrame(int frameNumber) {
        this.frameNumber = frameNumber;
        addBallToFrame();
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getFrameScore() {
        return frameScore;
    }

    public List<Ball> getFrameBalls() {
        return frameBalls;
    }

    private void addBallToFrame() {
        frameBalls = Stream.generate(Ball::new).limit(2).collect(Collectors.toList());
    }

    public void addThirdBall() {
        if (frameNumber == 10 ) {
            frameBalls.add(new Ball());
        }
    }

    public void setFrameScore(int score) {
        this.frameScore = score;
    }
}
