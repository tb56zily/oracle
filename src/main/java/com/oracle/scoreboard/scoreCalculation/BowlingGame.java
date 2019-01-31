package com.oracle.scoreboard.scoreCalculation;

import com.oracle.scoreboard.model.Ball;
import com.oracle.scoreboard.model.BowlingFrame;
import com.oracle.scoreboard.scoreValidation.BowlingGameValidator;
import com.oracle.scoreboard.util.BowlingFrameHelper;

import java.util.List;
import java.util.stream.IntStream;

import static com.oracle.scoreboard.constatnts.BowlingGameConstant.*;

public class BowlingGame {

    public Integer[] calculateScore(int[] gameScore) {
        if (validateGameScore(gameScore)) {
            List<BowlingFrame> frames =  BowlingFrameHelper.createFramesAndAssignPinsDown(gameScore);
            calculateFrameScore(frames);
            return frames.stream().map(BowlingFrame::getFrameScore).toArray(Integer[]::new);
        }
        return IntStream.iterate(-1, i -> -1).limit(1).boxed().toArray(Integer[]::new);
    }

    /**
     * @param gameScore, score having pins down for each ball
     * @return true if score is valid, else false
     */
    private boolean validateGameScore(int[] gameScore) {
         if(BowlingGameValidator.validateNumberOfBalls(gameScore.length)){
             return BowlingGameValidator.validatePinsDownPerBall(gameScore);
         }
         return false;
    }

    /**
     * Calculates score for each frame based on the pins down for each ball
     * @param frames list of frames, each frame having balls with their pins
     */
    private void calculateFrameScore(List<BowlingFrame> frames) {
        int frameScore = 0;
        for (int i = 0; i < frames.size() - 1; i++) {
            BowlingFrame currentFrame = frames.get(i);
            int ball1Score = currentFrame.getFrameBalls().get(BALL_1).getBallScore();
            int ball2Score = currentFrame.getFrameBalls().get(BALL_2).getBallScore();
            // if it is a 'Strike frame'
            if (ball1Score == STRIKE_SCORE) {//
                frameScore += calculateStrikeFrameScore(frames, i);
            } else if (ball1Score + ball2Score == MAX_BALL_SCORE) {
                // if it is a 'Spare frame'
                frameScore += calculateSpareFrameScore(frames, i);
            } else {
                // Frame with neither Strike nor Spare
                frameScore += ball1Score + ball2Score;
            }
            currentFrame.setFrameScore(frameScore);
        }
        //To calculate 10th frame score
        calculateLastFrameScore(frames.get(8).getFrameScore(), frames.get(9));
    }

    /**
     * calculates 10th frame by summing up the pins down
     * for the last frame's all balls and adds previous
     * previous frame's score.
     * @param previousFrameScore, score of ninth frame
     * @param lastFrame tenth/last frame of a game
     */
    private void calculateLastFrameScore(int previousFrameScore, BowlingFrame lastFrame) {
        int lastFrameScore = lastFrame.getFrameBalls().stream().mapToInt(Ball::getBallScore).sum();
        lastFrame.setFrameScore(previousFrameScore + lastFrameScore);
    }

    /**
     * Calculate frame score if the frame is a Strike frame
     * by using pins down for next two balls.
     * @param frames, all the frames
     * @param frameNumber, frame number of the current frame/Strike frame
     * @return score of an Strike frame
     */
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

    /**
     * Calculate frame score if the frame is a Spare frame
     * by using pins down for next one ball.
     * @param frames, all the frames
     * @param frameNumber, frame number of the current frame/
     * @return score of an Strike frame
     */
    private int calculateSpareFrameScore(List<BowlingFrame> frames, int frameNumber) {
        BowlingFrame nextFrame = frames.get(frameNumber + 1);
        List<Ball> frameBalls = nextFrame.getFrameBalls();
        int ball1Score = frameBalls.get(BALL_1).getBallScore();
        return MAX_BALL_SCORE + ball1Score;
    }

}