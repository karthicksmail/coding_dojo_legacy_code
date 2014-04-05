package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	
	Players players = new Players();
	boolean isGettingOutOfPenaltyBox;

	public Game() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {

		players.addPlayer(playerName);
		places[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

		announce(playerName + " was added");
		return true;
	}

	public int howManyPlayers() {
		return players.numberOfPlayers();
	}

	public void roll(int roll) {
		announce(players.currentPlayerName() + " is the current player");
		announce("They have rolled a " + roll);

		if (inPenaltyBox[players.currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				announce(players.currentPlayerName()
						+ " is getting out of the penalty box");
				moveAndAnnounce(roll);
			} else {
				announce(players.currentPlayerName()
						+ " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			moveAndAnnounce(roll);
		}

	}

	private void moveAndAnnounce(int roll) {
		places[players.currentPlayer] = places[players.currentPlayer] + roll;
		if (places[players.currentPlayer] > 11)
			places[players.currentPlayer] = places[players.currentPlayer] - 12;

		announce(players.currentPlayerName() + "'s new location is "
				+ places[players.currentPlayer]);
		announce("The category is " + currentCategory());

		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			announce(popQuestions.removeFirst().toString());
		if (currentCategory() == "Science")
			announce(scienceQuestions.removeFirst().toString());
		if (currentCategory() == "Sports")
			announce(sportsQuestions.removeFirst().toString());
		if (currentCategory() == "Rock")
			announce(rockQuestions.removeFirst().toString());
	}

	private String currentCategory() {
		if (places[players.currentPlayer] == 0)
			return "Pop";
		if (places[players.currentPlayer] == 4)
			return "Pop";
		if (places[players.currentPlayer] == 8)
			return "Pop";
		if (places[players.currentPlayer] == 1)
			return "Science";
		if (places[players.currentPlayer] == 5)
			return "Science";
		if (places[players.currentPlayer] == 9)
			return "Science";
		if (places[players.currentPlayer] == 2)
			return "Sports";
		if (places[players.currentPlayer] == 6)
			return "Sports";
		if (places[players.currentPlayer] == 10)
			return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[players.currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				inPenaltyBox[players.currentPlayer] = false;
				boolean winner = anounceGoldCoinRotatePlayer();

				return winner;
			} else {
				rotatePlayer();
				return true;
			}

		} else {
			boolean winner = anounceGoldCoinRotatePlayer();

			return winner;
		}
	}

	private void rotatePlayer() {
		players.currentPlayer++;
		if (players.currentPlayer == players.numberOfPlayers())
			players.currentPlayer = 0;
	}

	private boolean anounceGoldCoinRotatePlayer() {
		announce("Answer was correct!!!!");
		purses[players.currentPlayer]++;
		announce(players.currentPlayerName() + " now has "
				+ purses[players.currentPlayer] + " Gold Coins.");

		boolean winner = didPlayerWin();
		rotatePlayer();
		return winner;
	}

	protected void announce(String message) {
		System.out.println(message);
	}

	public boolean wrongAnswer() {
		announce("Question was incorrectly answered");
		announce(players.currentPlayerName() + " was sent to the penalty box");
		inPenaltyBox[players.currentPlayer] = true;

		rotatePlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[players.currentPlayer] == 6);
	}
}
