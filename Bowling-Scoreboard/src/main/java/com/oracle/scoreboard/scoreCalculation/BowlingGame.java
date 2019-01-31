package com.oracle.scoreboard.scoreCalculation;

import com.oracle.scoreboard.model.Ball;
import com.oracle.scoreboard.model.BowlingFrame;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BowlingGame {

    private static final int MAX_FRAMES = 10;
    private static final int MAX_BALL_SCORE = 10;
    private static final int STRIKE_SCORE = 10;
    private static final int SPARE_SCORE = 10;
    private static final int BALL_1 = 0;
    private static final int BALL_2 = 1;

    public Integer[] calculateScore(int[] gameScore) {
        if (validateGameScore(gameScore)) {
            List<BowlingFrame> frames = createEmptyFrames();
            assignPinsDownPerFrame(frames, gameScore);
            calculateFrameScore(frames);
            return frames.stream().map(BowlingFrame::getFrameScore).toArray(Integer[]::new);
        }
        return IntStream.iterate(-1, i -> -1).limit(1).boxed().toArray(Integer[]::new);
    }

    private boolean validateGameScore(int[] gameScore) {
        if (gameScore.length > 21) {
            return false;
        }
        return Arrays.stream(gameScore).parallel()
                .noneMatch(score -> score < 0 || score > 10);
    }

    private List<BowlingFrame> createEmptyFrames() {
        return Stream.iterate(1, frameNumber -> frameNumber + 1)
                .limit(MAX_FRAMES)
                .map(BowlingFrame::new)
                .collect(Collectors.toList());
    }

    /**
     * @param frames
     * @param score
     */
    private void assignPinsDownPerFrame(List<BowlingFrame> frames, int[] score) {
        int frameNumber = 0;
        for (int pinsDown : score) {
            BowlingFrame bowlingFrame = frames.get(frameNumber);
            List<Ball> frameBalls = bowlingFrame.getFrameBalls();
            Ball ball1 = frameBalls.get(BALL_1);
            Ball ball2 = frameBalls.get(BALL_2);
            if (ball1.getBallScore() == -1) {
                ball1.addBallScore(pinsDown);
                if (pinsDown == STRIKE_SCORE && frameNumber != 9) {
                    frameNumber++;
                }
            } else if (ball2.getBallScore() == -1) {
                ball2.addBallScore(pinsDown);
                if (frameNumber != 9) {
                    frameNumber++;
                }
            } else {
                bowlingFrame.addThirdBall();
                frameBalls.get(2).addBallScore(pinsDown);
            }
        }
    }

    /**
     * @param frames
     */
    private void calculateFrameScore(List<BowlingFrame> frames) {
        int frameScore = 0;
        for (int i = 0; i < frames.size() - 1; i++) {
            BowlingFrame currentFrame = frames.get(i);
            int ball1Score = currentFrame.getFrameBalls().get(BALL_1).getBallScore();
            int ball2Score = currentFrame.getFrameBalls().get(BALL_2).getBallScore();
            if (ball1Score == STRIKE_SCORE) {//STRIKE FRAME
                frameScore += calculateStrikeFrameScore(frames, i);
            } else if (ball1Score + ball2Score == MAX_BALL_SCORE) {//SPARE FRAME
                frameScore += calculateSpareFrameScore(frames, i);
            } else {
                frameScore += ball1Score + ball2Score;
            }
            currentFrame.setFrameScore(frameScore);
        }
        calculateLastFrameScore(frames);
    }

    private void calculateLastFrameScore(List<BowlingFrame> frames) {
        int previousFrameScore = frames.get(8).getFrameScore();
        BowlingFrame lastFrame = frames.get(9);
        int lastFrameScore = lastFrame.getFrameBalls().stream().mapToInt(Ball::getBallScore).sum();
        lastFrame.setFrameScore(previousFrameScore + lastFrameScore);
    }

    private int calculateStrikeFrameScore(List<BowlingFrame> frames, int frameNumber) {
        BowlingFrame nextFrame1 = frames.get(frameNumber + 1);
        List<Ball> frameBalls = nextFrame1.getFrameBalls();
        int ball1Score = frameBalls.get(BALL_1).getBallScore();
        int ball2Score = frameBalls.get(BALL_2).getBallScore();
        if (ball1Score == STRIKE_SCORE) {
            if (frameNumber != 8) {
                return MAX_BALL_SCORE + ball1Score + frames.get(frameNumber + 2).getFrameBalls().get(BALL_1).getBallScore();
            } else {
                return MAX_BALL_SCORE + ball1Score + ball2Score;
            }
        }
        if (ball2Score == SPARE_SCORE) {
            return MAX_BALL_SCORE + SPARE_SCORE;
        }
        return MAX_BALL_SCORE + ball1Score + ball2Score;
    }

    private int calculateSpareFrameScore(List<BowlingFrame> frames, int frameNumber) {
        BowlingFrame nextFrame = frames.get(frameNumber + 1);
        List<Ball> frameBalls = nextFrame.getFrameBalls();
        int ball1Score = frameBalls.get(BALL_1).getBallScore();
        return MAX_BALL_SCORE + ball1Score;
    }

}
