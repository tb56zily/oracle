package com.oracle.scoreboard.util;

import com.oracle.scoreboard.constatnts.BowlingGameConstant;
import com.oracle.scoreboard.model.Ball;
import com.oracle.scoreboard.model.BowlingFrame;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.oracle.scoreboard.constatnts.BowlingGameConstant.*;

public class BowlingFrameHelper {

    /**
     * Create an empty list of Frames and then assigns the pins down
     * for each ball per each frame using the bowling game score passed as input
     * @param score, score of a bowling game
     * @return list of frames with pins per ball
     */
    public static List<BowlingFrame> createFramesAndAssignPinsDown(int[] score) {
        List<BowlingFrame> frames  =    Stream.iterate(1, frameNum -> frameNum + 1)
                .limit(BowlingGameConstant.MAX_FRAMES)
                .map(BowlingFrame::new)
                .collect(Collectors.toList());
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
        return  frames;
    }
}
