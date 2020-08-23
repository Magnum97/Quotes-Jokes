package me.magandgolden;

import co.aikar.commands.BukkitCommandManager;
import de.leonhard.storage.Yaml;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class QuotesJokes extends JavaPlugin {

	public void onEnable () {
		createConfigs();
		register();
	}

	private void register() {
		BukkitCommandManager manager = new BukkitCommandManager(this);
		manager.registerCommand(new Random());
	}

	private void createConfigs () {
		// This will create "quotes.yml" in the plugins' folder.
		Yaml quoteFile = new Yaml("quotes", getDataFolder().toString(), getResource("quotes.yml"));
		Yaml jokeFile = new Yaml("jokes", getDataFolder().toString(), getResource("jokes.yml"));


		// When making object like list, or Set you need to specify what will be in the list/set
		// with < > around it.

		// Note two ways to do this. You can do it on two lines
		List <String> quoteList = new ArrayList <>();
		quoteList = quoteFile.getStringList("quotes");

		// or combine it and do on one line.
		List <String> jokeList = jokeFile.getStringList("jokes");
	}
}
