package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
	private ArrayList data = new ArrayList();
	public int currentPlayer = 0;
	
	public void addPlayer(String playerName) {
		data.add(playerName);
	}

	public int numberOfPlayers() {
		return data.size();
	}

	String currentPlayerName() {
		return data.get(currentPlayer).toString();
	}

}