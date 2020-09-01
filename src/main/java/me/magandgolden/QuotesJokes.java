package me.magandgolden;

import co.aikar.commands.BukkitCommandManager;
import de.leonhard.storage.Yaml;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class QuotesJokes extends JavaPlugin {

	@Getter
	private static QuotesJokes plugin;
	@Getter
	private Yaml quoteFile;
	@Getter
	private Yaml jokeFile;
	@Getter
	private Yaml cfg;


	public void onEnable () {
		plugin = this;
		createConfigs();
		addCommands();
	}

	private void addCommands () {
		BukkitCommandManager commandManager = new BukkitCommandManager(plugin);
		//noinspection deprecation
		commandManager.enableUnstableAPI("help");
		commandManager.registerCommand(new MainCommand());
	}

	private void createConfigs () {
		// This will create "quotes.yml" in the plugins' folder
		// or load if they already exist.
		quoteFile = new Yaml("quotes", getDataFolder().toString() + File.separator + "quotes", getResource("quotes.yml"));
		jokeFile = new Yaml("jokes", getDataFolder().toString() + File.separator + "jokes", getResource("jokes.yml"));
		cfg = new Yaml("config", getDataFolder().toString(), getResource("config.yml"));
	}
}
