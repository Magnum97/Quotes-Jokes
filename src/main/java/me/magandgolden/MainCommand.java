package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import de.leonhard.storage.Yaml;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias ("random")
public class MainCommand extends BaseCommand {

	private static final QuotesJokes plugin = QuotesJokes.getPlugin();

	@Subcommand ("joke")
	private static void onJoke (CommandSender sender, @Optional String action, @Optional String[] arg2) {
		Yaml jokes = plugin.getJokeFile();
		if (action != null) {
			if (action.equals("add")) {
				if (arg2.length < 1) { // If no joke is added, send message
					sender.sendMessage("I need to know what to add.");
					sender.sendMessage("Use: /random joke add <joke>");
				}
				// if joke is added to command, add to file.
				jokes.getStringList("jokes").add(Arrays.toString(arg2));
				jokes.write();
				sender.sendMessage("Your joke was added!");
				return;
			}
			if (action.equals("list")) {
				for (int i = 0; i < jokes.getStringList("jokes").size(); i++) {
					sender.sendMessage((i + 1) + ": " + jokes.getStringList("jokes").get(i));
				}
			return;
			}
			int JokeCount = plugin.getJokeFile().getStringList("jokes").size();
			int choose = ThreadLocalRandom.current().nextInt(JokeCount);
			sender.sendMessage(plugin.getJokeFile().getStringList("jokes").get(choose));
		}
	}

	@Subcommand ("quote")
	private static void onQuote (CommandSender sender, @Optional String action, @Optional String[] arg2) {
		Yaml quotes = plugin.getQuoteFile();
		if (action != null) {
			if (action.equals("add")) {
				if (arg2.length < 1) {
					sender.sendMessage("I need to know what to add.");
					sender.sendMessage("Use: /random quote add <quote>");
				}
				quotes.getStringList("quotes").add(Arrays.toString(arg2));
				quotes.write();
				sender.sendMessage("Your quote was added!");
				return;
			}
			int QuoteCount = plugin.getQuoteFile().getStringList("quotes").size();
			int choose = ThreadLocalRandom.current().nextInt(QuoteCount);
			sender.sendMessage(plugin.getQuoteFile().getStringList("quotes").get(choose));
		}
	}
}

/*
	@Default // Leave this for later
	private static void defaultCommand (CommandSender sender, String type, @Optional String action, @Optional String arg2) {

	}
*/

