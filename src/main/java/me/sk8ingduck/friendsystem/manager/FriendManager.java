package me.sk8ingduck.friendsystem.manager;

import me.sk8ingduck.friendsystem.mysql.MySQL;
import me.sk8ingduck.friendsystem.util.FriendPlayer;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * FriendManager retrieves friend data from MySQL.
 * <p>
 * FriendManager offers both synchronous and asynchronous methods for retrieving FriendPlayer data.
 * Asynchronous usage is recommended to minimize server lag.
 */
public class FriendManager {

	private final MySQL mySQL;

	public FriendManager(MySQL mySQL) {
		this.mySQL = mySQL;
	}

	public FriendPlayer getFriendPlayer(String name) {
		return mySQL.getFriendPlayer(name);
	}

	public void getFriendPlayer(String name, Consumer<FriendPlayer> friendPlayer) {
		mySQL.getFriendPlayer(name, friendPlayer);
	}

	public FriendPlayer getFriendPlayer(UUID uuid) {
		return mySQL.getFriendPlayer(uuid);
	}

	public void getFriendPlayer(UUID uuid, Consumer<FriendPlayer> friendPlayer) {
		mySQL.getFriendPlayer(uuid, friendPlayer);
	}
}