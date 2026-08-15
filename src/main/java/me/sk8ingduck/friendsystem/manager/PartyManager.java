package me.sk8ingduck.friendsystem.manager;

import me.sk8ingduck.friendsystem.mysql.MySQL;
import me.sk8ingduck.friendsystem.util.Party;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * PartyManager retrieves party data from MySQL.
 * <p>
 * PartyManager offers both synchronous and asynchronous methods for handling Party objects.
 * Asynchronous usage is recommended to minimize server lag.
 */
public class PartyManager {

	private final MySQL mySQL;

	public PartyManager(MySQL mySQL) {
		this.mySQL = mySQL;
	}

	public Party getParty(UUID playerUUID) {
		return mySQL.getParty(playerUUID);
	}

	public void getParty(UUID playerUUID, Consumer<Party> party) {
		mySQL.getParty(playerUUID, party);
	}

	public List<Party> getAllParties() {
		return mySQL.getAllParties();
	}

	public void getAllParties(Consumer<List<Party>> parties) {
		mySQL.getAllParties(parties);
	}
}