package com.adaptionsoft.games.trivia;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameTest {
	GameUnderTest game = new GameUnderTest();

	@Test
	public void rollANumberShouldAnnounceCurrentPlayerAndLocation() throws Exception {
		game.add("Stanly");
		game.roll(1);
		
		actualMessagesHas("Stanly is the current player");
		actualMessagesHas("Stanly's new location is 1");
	}

	@Test
	public void correctlyAnsweredShouldAnnounceAdditionOfGoldCoin() throws Exception {
		game.add("Stanly");
		
		game.roll(1);
		game.wasCorrectlyAnswered();
		
		actualMessagesHas("Answer was correct!!!!");
		actualMessagesHas("Stanly now has 1 Gold Coins.");
	}
	
	@Test
	public void wronglyAnsweredShouldAnnounceSendToPenaltyBox() throws Exception {
		game.add("Stanly");
		
		game.roll(1);
		game.wrongAnswer();

		actualMessagesHas("Question was incorrectly answered");
		actualMessagesHas("Stanly was sent to the penalty box");
	}
	
	@Test
	public void oddNumberRollShouldAnnounceOutOfPenaltyBoxAndShouldOnlyAnnounceOnce() {
		game.add("Stanly");
		
		game.roll(1);
		game.wrongAnswer();

		game.messages.clear();
		game.roll(1);

		assertEquals("Stanly is the current player", game.messages.get(0));
		assertEquals("They have rolled a 1", game.messages.get(1));
		assertEquals("Stanly is getting out of the penalty box", game.messages.get(2));
		assertEquals("Stanly's new location is 2", game.messages.get(3));
		assertEquals("The category is Sports", game.messages.get(4));
		assertEquals("Sports Question 0", game.messages.get(5));
		
		game.messages.clear();
		game.wasCorrectlyAnswered();
		assertEquals("Answer was correct!!!!", game.messages.get(0));
		assertEquals("Stanly now has 1 Gold Coins.", game.messages.get(1));
		
		game.messages.clear();
		game.roll(1);
		assertEquals("Stanly is the current player", game.messages.get(0));
		assertEquals("They have rolled a 1", game.messages.get(1));
		assertFalse(game.messages.contains("Stanly is getting out of the penalty box"));
	}

	protected void actualMessagesHas(String message) {
		assertTrue(game.messages.contains(message));
	}
	
	public class GameUnderTest extends Game {
		public List<String> messages = new ArrayList<String>();
		@Override
		protected void announce(String message) {
			this.messages.add(message);
		}
	}
	
}