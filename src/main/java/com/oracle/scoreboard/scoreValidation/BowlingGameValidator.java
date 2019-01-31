package com.oracle.scoreboard.scoreValidation;

import java.util.Arrays;

import static com.oracle.scoreboard.constatnts.BowlingGameConstant.MAX_BALLS;

public interface BowlingGameValidator {

    static boolean validatePinsDownPerBall(int[] gameScore){
        return Arrays.stream(gameScore).parallel()
                .noneMatch(score -> score < 0 || score > MAX_BALLS);
    }

    static boolean validateNumberOfBalls(int scoreLength){
        return scoreLength<=21;
    }

}
