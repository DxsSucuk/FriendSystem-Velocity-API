package me.sk8ingduck.friendsystem;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.sk8ingduck.friendsystem.config.DBConfig;
import me.sk8ingduck.friendsystem.manager.FriendManager;
import me.sk8ingduck.friendsystem.manager.PartyManager;
import me.sk8ingduck.friendsystem.mysql.MySQL;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "friendsystem-api", name = "FriendSystem-Velocity-API",
		version = "1.0", authors = {"sk8ingduck"})
public final class FriendSystemAPI {

	private static FriendSystemAPI instance;
	private FriendManager friendManager;
	private PartyManager partyManager;
	private MySQL mysql;

	@Inject
	public FriendSystemAPI(EventManager eventManager, Logger logger, @DataDirectory Path dataDirectory) {
		instance = this;

		DBConfig db = new DBConfig("database.yml", dataDirectory.getParent().resolve("FriendSystem"));
		mysql = new MySQL(db.getHost(), db.getPort(), db.getUsername(), db.getPassword(), db.getDatabase());

		friendManager = new FriendManager(mysql);
		partyManager = new PartyManager(mysql);

		eventManager.register(this, this);
		logger.info("FriendSystem-Velocity-API enabled.");
	}

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
		mysql.close();
	}

	public static FriendSystemAPI getInstance() {
		return instance;
	}

	public FriendManager getFriendManager() {
		return friendManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}
}