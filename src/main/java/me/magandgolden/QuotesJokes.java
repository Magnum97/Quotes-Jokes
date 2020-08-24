package me.magandgolden;

import co.aikar.commands.BukkitCommandManager;
import de.leonhard.storage.Yaml;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class QuotesJokes extends JavaPlugin {

	@Getter
	private Plugin instance;
	@Getter
	private Yaml quoteFile;
	@Getter
	private Yaml jokeFile;


	public void onEnable () {
		instance = this;
		createConfigs();
		addCommands();
	}

	private void addCommands () {
		BukkitCommandManager commandManager = new BukkitCommandManager(instance);
		//noinspection deprecation
		commandManager.enableUnstableAPI("help");
		commandManager.registerCommand(new MainCommand());
	}

	private void createConfigs () {
		// This will create "quotes.yml" in the plugins' folder
		// or load if they already exist.
		quoteFile = new Yaml("quotes", getDataFolder().toString(), getResource("quotes.yml"));
		jokeFile = new Yaml("jokes", getDataFolder().toString(), getResource("jokes.yml"));
	}
}
