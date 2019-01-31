package com.oracle.scoreboard.test;

import com.oracle.scoreboard.scoreCalculation.BowlingGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

public class ScoreboardTest {


    private BowlingGame bowlingGame;

    @Before
    public void setUp() {
        bowlingGame = new BowlingGame();
    }

    @Test
    public void TestNoStrikeNoSpare() {
        int[] score = {1, 0, 1, 5, 0, 8, 1, 8, 0, 1, 8, 1, 1, 0, 1, 2, 4, 5, 5, 4};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 1);
        validateFrameScore(frameScores, 2, 7);
        validateFrameScore(frameScores, 3, 15);
        validateFrameScore(frameScores, 4, 24);
        validateFrameScore(frameScores, 5, 25);
        validateFrameScore(frameScores, 6, 34);
        validateFrameScore(frameScores, 7, 35);
        validateFrameScore(frameScores, 8, 38);
        validateFrameScore(frameScores, 9, 47);
        validateFrameScore(frameScores, 10, 56);
        validateTotalScore("NoStrikeNoSpare", frameScores, 56);
    }

    @Test
    public void TestAllStrikes() {
        int[] score = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 30);
        validateFrameScore(frameScores, 2, 60);
        validateFrameScore(frameScores, 3, 90);
        validateFrameScore(frameScores, 4, 120);
        validateFrameScore(frameScores, 5, 150);
        validateFrameScore(frameScores, 6, 180);
        validateFrameScore(frameScores, 7, 210);
        validateFrameScore(frameScores, 8, 240);
        validateFrameScore(frameScores, 9, 270);
        validateFrameScore(frameScores, 10, 300);
        validateTotalScore("AllStrikes", frameScores, 300);
    }

    @Test
    public void TestMissStrikeSpare() {
        int[] score = {1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 5);
        validateFrameScore(frameScores, 2, 14);
        validateFrameScore(frameScores, 3, 29);
        validateFrameScore(frameScores, 4, 49);
        validateFrameScore(frameScores, 5, 60);
        validateFrameScore(frameScores, 6, 61);
        validateFrameScore(frameScores, 7, 77);
        validateFrameScore(frameScores, 8, 97);
        validateFrameScore(frameScores, 9, 117);
        validateFrameScore(frameScores, 10, 133);
        validateTotalScore("TestMissStrikeSpare", frameScores, 133);
    }

    @Test
    public void TestOnlyLastFrameStrike() {
        int[] score = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 10};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 0);
        validateFrameScore(frameScores, 2, 0);
        validateFrameScore(frameScores, 3, 0);
        validateFrameScore(frameScores, 4, 0);
        validateFrameScore(frameScores, 5, 0);
        validateFrameScore(frameScores, 6, 0);
        validateFrameScore(frameScores, 7, 0);
        validateFrameScore(frameScores, 8, 0);
        validateFrameScore(frameScores, 9, 0);
        validateFrameScore(frameScores, 10, 20);
        validateTotalScore("TestOnlyLastFrameStrike", frameScores, 20);
    }

    @Test
    public void TestAlternateStrike() {
        int[] score = {10, 3, 1, 10, 0, 6, 10, 0, 0, 10, 5, 2, 10, 0, 10, 5};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 14);
        validateFrameScore(frameScores, 2, 18);
        validateFrameScore(frameScores, 3, 34);
        validateFrameScore(frameScores, 4, 40);
        validateFrameScore(frameScores, 5, 50);
        validateFrameScore(frameScores, 6, 50);
        validateFrameScore(frameScores, 7, 67);
        validateFrameScore(frameScores, 8, 74);
        validateFrameScore(frameScores, 9, 94);
        validateTotalScore("TestAlternateStrike", frameScores, 109);
    }

    @Test
    public void TestAllSpare() {
        int[] score = {1, 9, 2, 8, 3, 7, 4, 6, 5, 5, 6, 4, 7, 3, 0, 10, 8, 2, 9, 1, 6};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 12);
        validateFrameScore(frameScores, 2, 25);
        validateFrameScore(frameScores, 3, 39);
        validateFrameScore(frameScores, 4, 54);
        validateFrameScore(frameScores, 5, 70);
        validateFrameScore(frameScores, 6, 87);
        validateFrameScore(frameScores, 7, 97);
        validateFrameScore(frameScores, 8, 115);
        validateFrameScore(frameScores, 9, 134);
        validateTotalScore("TestAlternateSpare", frameScores, 150);
    }

    @Test
    public void TestRandomInput() {
        int[] score = {10, 0, 10, 10, 5, 0, 8, 2, 9, 0, 10, 8, 1, 1, 0, 4, 6, 10};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 20);
        validateFrameScore(frameScores, 2, 40);
        validateFrameScore(frameScores, 3, 55);
        validateFrameScore(frameScores, 4, 60);
        validateFrameScore(frameScores, 5, 79);
        validateFrameScore(frameScores, 6, 88);
        validateFrameScore(frameScores, 7, 107);
        validateFrameScore(frameScores, 8, 116);
        validateFrameScore(frameScores, 9, 117);
        validateTotalScore("Test1", frameScores, 137);
    }

    @Test
    public void TestInvalidPins() {
        int[] score = {6, 12, 7, 1, 10, 9, 0, 8, 2, 10, 10, 3, 5, 7, 2, 5, 5, 8};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        int frameScore = frameScores[0];
        Assert.assertEquals(frameScores.length, 1);
        Assert.assertEquals(frameScore, -1);
    }

    @Test
    public void TestNegativePinValues() {
        int[] score = {6, -2, 7, 1, 10, 9, 0, 8, 2, 10, 10, 3, 5, 7, 2, 5, 5, 8};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        int frameScore = frameScores[0];
        Assert.assertEquals(frameScores.length, 1);
        Assert.assertEquals(frameScore, -1);
    }

    @Test
    public void TestInvalidNumberOfBalls() {
        int[] score = {6, 2, 7, 1, 10, 9, 0, 8, 2, 10, 10, 3, 5, 7, 2, 5, 5, 8, 1, 5, 2, 10};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        int frameScore = frameScores[0];
        Assert.assertEquals(frameScores.length, 1);
        Assert.assertEquals(frameScore, -1);
    }

    @Test
    public void TestTryInput() {
        int[] score = {6, 2, 7, 1, 10, 9, 0, 8, 2, 10, 10, 3, 5, 7, 2, 5, 5, 8};
        Integer[] frameScores = bowlingGame.calculateScore(score);
        validateFrameScore(frameScores, 1, 8);
        validateFrameScore(frameScores, 2, 16);
        validateFrameScore(frameScores, 3, 35);
        validateFrameScore(frameScores, 4, 44);
        validateFrameScore(frameScores, 5, 64);
        validateFrameScore(frameScores, 6, 87);
        validateFrameScore(frameScores, 7, 105);
        validateFrameScore(frameScores, 8, 113);
        validateFrameScore(frameScores, 9, 122);
        validateTotalScore("Test2", frameScores, 140);
    }

    private void validateFrameScore(Integer[] frameScores, int frameNumber, int expectedFrameScore) {
        int actualFrameScore = IntStream.range(0, frameScores.length)
                .map(i -> frameScores[frameNumber - 1])
                .findAny()
                .orElse(-1);
        Assert.assertEquals(expectedFrameScore, actualFrameScore);
    }

    private void validateTotalScore(String testCase, Integer[] frameScores, int expectedTotalScore) {
        int actualTotalScore = IntStream.range(0, frameScores.length)
                .map(i -> frameScores[9])
                .findAny()
                .orElse(-1);
        Assert.assertEquals(expectedTotalScore, actualTotalScore);
        System.out.println("TestCase::" + testCase);
        System.out.println("Frame No.  ->  " + " Frame Score");
        IntStream.range(0, frameScores.length)
                .forEach(frameNumber -> System.out.println("Frame " + (frameNumber + 1) + "    ->   " + frameScores[frameNumber]));
    }
}