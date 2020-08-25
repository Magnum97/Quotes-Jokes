package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import de.leonhard.storage.Yaml;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias ("random")
public class MainCommand extends BaseCommand {

	private static final QuotesJokes plugin = QuotesJokes.getPlugin();

	@Subcommand ("joke")
	private static void onJoke (CommandSender sender, @Default ("send") String action, @Default ("") String[] arg2) {
		Yaml jokeFile = plugin.getJokeFile();
		List <String> jokes = plugin.getJokeFile().getStringList("jokes");
		switch (action) {
			case "send":
				sendRandom(sender, jokes);
				break;
			case "list":
				sendList(sender, jokes);
				break;
			case "add":
				addList(sender, jokes, arg2);
				break;
			case "remove":
				int number = Integer.parseInt(arg2.toString());
				removeList(sender, jokes, number);
		}

	}

	@Subcommand ("quote")
	private static void onQuote (CommandSender sender, @Optional String action, @Optional String arg2) {
		List <String> quotes = plugin.getQuoteFile().getStringList("quotes");
		if (action.isEmpty()) {
			sendRandom(sender, quotes);
		}
		int QuoteCount = (int) plugin.getQuoteFile().getStringList("quotes").size();
		int choose = ThreadLocalRandom.current().nextInt(QuoteCount);
		sender.sendMessage(plugin.getQuoteFile().getStringList("quotes").get(choose));
	}
}

	private static void addList (CommandSender sender, List <String> list, String[] array) {
		StringBuilder builder = new StringBuilder();
		for (String string : array) {
			builder.append(string);
		}
		String add = builder.toString();
		list.add(add);
		sender.sendMessage(add + " has been added");
	}

	private static void sendList (CommandSender sender, List <String> list) {
		for (int i = 0;i<list.size();i++){
			sender.sendMessage(i+": "+list.get(i));
		}

	}

	private static void removeList (CommandSender sender, List <String> list, int i) {
sender.sendMessage("Removed #"+i+": "+list.get(i-1));

	}

	private static void sendRandom (CommandSender sender, List <String> list) {
		int jokeCount = list.size();
		int choice = ThreadLocalRandom.current().nextInt(jokeCount);
		sender.sendMessage(list.get(choice));
	}

/*
	@Default // Leave this for later
	private static void defaultCommand (CommandSender sender, String type, @Optional String action, @Optional String arg2) {

	}
*/

