package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias ("random")
public class MainCommand extends BaseCommand {

	private static final QuotesJokes plugin = QuotesJokes.getPlugin();

	@Subcommand ("joke")
	// @Optional items in commands give me problems. Found it easier to have default set if left empty by user
	private static void onJoke (CommandSender sender, @Default ("send") String action, @Default ("0") String[] numberOrText) {
		Yaml jokeFile = plugin.getJokeFile();
		List <String> jokeList = jokeFile.getStringList("jokes");

		/* switch is like multiple 'if' statements
		 it is possible that none are matched or run.
		 It is also possible to run multiple case statements.
		 */
		switch (action) {
			case "send":
				sendRandom(sender, jokeList);
				break; // If no break is set it will continue to run the next case
			case "add":
				addFile(sender, jokeFile, "jokes", joinString(numberOrText));
			case "list": // What happens after you add a joke?
				sendList(sender, jokeList);
				break;
			case "remove":
				int number = Integer.parseInt(joinString(numberOrText));
				removeFile(sender, jokeFile, "jokes", number);
				sendList(sender, jokeList);
		}

	}

	@Subcommand ("quote")
	private static void onQuote (CommandSender sender, @Default ("send") String action, @Default ("0") String[] numberOrText) {
		Yaml quoteFile = plugin.getQuoteFile();
		List <String> quoteList = quoteFile.getStringList("quotes");
		switch (action) {
			case "send":
				sendRandom(sender, quoteList);
				break;
			case "add":
				addFile(sender, quoteFile, "quotes", joinString(numberOrText));
			case "list":
				sendList(sender, quoteList);
				break;
			case "remove":
				int number = Integer.parseInt(joinString(numberOrText));
				removeFile(sender, quoteFile, "quotes", number);
				sendList(sender, quoteList);
		}
	}

	private static void addFile (CommandSender sender, Yaml file, String key, String text) {
		List <String> list = file.getStringList(key);
		list.add(text);
		file.set(key, list);
		file.write();
		sender.sendMessage(text + " has been added to " + key);
	}

	private static void sendList (CommandSender sender, List <String> list) {
		for (int i = 0; i < list.size(); i++) {
			String message = ChatColor.translateAlternateColorCodes('&',list.get(i));
			sender.sendMessage((i + 1) + ": " + message);
		}

	}

	private static void removeFile (CommandSender sender, Yaml file, String key, int remove) {
		 /*
			we do this because humans read a list as 1-9
		    but java starts counting at 0.
		 */
		remove--;
		List <String> list = file.getStringList(key);
		if ((remove < 0) || (remove > (list.size() - 1))) {
			/* You can use ChatColor.[color] to send colored text.
			 * Another way to translate color you can use for user input / config file input:
			 * ChatColor.translateAlternateColorCodes('&',input);
			 */

			sender.sendMessage(ChatColor.RED + "Error! " + ChatColor.YELLOW + "Enter a number between 1 and " + list.size());
			return;
		}
		sender.sendMessage("Removed #" + (remove + 1) + ": " + list.get(remove));
		list.remove(remove);
		file.set(key, list);

	}

	private static void sendRandom (CommandSender sender, List <String> list) {
		int jokeCount = list.size();
		int choice = ThreadLocalRandom.current().nextInt(jokeCount);
		String message= ChatColor.translateAlternateColorCodes('&',list.get(choice));
		sender.sendMessage(message);
	}

	private static String joinString (String[] array) {
		CharSequence separator = " ";
		StringJoiner joiner = new StringJoiner(separator);
		for (String item : array) {
			joiner.add(item);
		}
		return joiner.toString();
	}

	@HelpCommand // Leave this for later
	private static void onHelp (CommandSender sender, CommandHelp help) {
		help.showHelp();
	}
}

