package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import de.leonhard.storage.Yaml;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias ("random")
public class MainCommand extends BaseCommand {

	private static QuotesJokes plugin = QuotesJokes.getPlugin();

	@Subcommand("joke")
	private static void onJoke(CommandSender sender, @Optional String action, @Optional String[] arg2) {
		Yaml jokes = plugin.getJokeFile();
		if (action != null) {
			if (action.equals("add")) {
				if (arg2.length < 1) {
					sender.sendMessage("I need to know what to add.");
					sender.sendMessage("Use: /random joke add <joke>");
				}
				jokes.getStringList("jokes").add(Arrays.toString(arg2));
				jokes.write();
				return;
			}
			int JokeCount = (int) plugin.getJokeFile().getStringList("jokes").size();
			int choose = ThreadLocalRandom.current().nextInt(JokeCount);
			sender.sendMessage(plugin.getJokeFile().getStringList("jokes").get(choose));
		}
	}

	@Subcommand("quote")
	private static void onQuote(CommandSender sender, @Optional String action, @Optional String[] arg2) {
		Yaml quotes = plugin.getQuoteFile();
		if (action != null) {
			if (action.equals("add")) {
				if (arg2.length < 1) {
					sender.sendMessage("I need to know what to add.");
					sender.sendMessage("Use: /random quote add <quote>");
				}
				quotes.getStringList("quotes").add(Arrays.toString(arg2));
				quotes.write();
				return;
			}
			int QuoteCount = (int) plugin.getQuoteFile().getStringList("quotes").size();
			int choose = ThreadLocalRandom.current().nextInt(QuoteCount);
			sender.sendMessage(plugin.getQuoteFile().getStringList("quotes").get(choose));
		}

/*
	@Default // Leave this for later
	private static void defaultCommand (CommandSender sender, String type, @Optional String action, @Optional String arg2) {

	}
*/
	}
}