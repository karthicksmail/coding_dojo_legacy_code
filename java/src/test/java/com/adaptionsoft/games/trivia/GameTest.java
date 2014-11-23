package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameTest {

	@Test
	public void testIfPlayerGetsOutOfPenaltyBox() {
		Game aGame = new Game();
		aGame.add("Karthick");
		aGame.roll(2);
		aGame.wrongAnswer();
		aGame.roll(3);
		aGame.wasCorrectlyAnswered();
		boolean isWinner = false;
		for (int i = 0; i < 5; i++) {
			aGame.roll(2);
			isWinner = !(aGame.wasCorrectlyAnswered());
		}
		assertTrue(isWinner);
	}
}
