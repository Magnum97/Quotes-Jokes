package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings ("unused")
public class MainCommand extends BaseCommand {

	private static final QuotesJokes plugin = QuotesJokes.getPlugin();

	@CommandAlias ("joke|jokes")
	// @Optional will return null if not provided in command. This needs to be checked before passing to methods
	private static void onJoke (CommandSender sender, @Default ("send") String action, @Optional String numberOrText) {
		Yaml jokeFile = plugin.getJokeFile();
		List <String> jokeList = new ArrayList <>();
		jokeFile.keySet().forEach(u -> jokeList.addAll(jokeFile.getStringList(u)));
		// If sender is console assign it the id 'console'
		String uuid = (sender instanceof Player) ? ((Player) sender).getUniqueId().toString() : "console";

		switch (action) {
			case "send":
				sendRandom(sender, jokeList);
				break;
			case "add":
				if (! sender.hasPermission("jokes.add")) {  // Check if sender has permission
					noPermission(sender, "jokes.add");  // if sender does NOT have permission
					return;// Pass sender and the permission then stop.
				}
				if (numberOrText == null) {
					// If you don't check and it passes null will throw exception
					sender.sendMessage(ChatColor.RED + "You need to enter something to add");
					return;
				}
				if (! userOverLimit(uuid, jokeFile, "jokes")) // Check how many jokes a user has added
					addFile(sender, jokeFile, uuid, numberOrText);
				else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', // New line is ignored
							"&fPlease remove a joke before adding more")); // Java continues reading until ;
					return;
				}
			case "list":
				if (! sender.hasPermission("jokes.list")) {
					noPermission(sender, "jokes.list");
					return;
				}
				sendList(sender, jokeFile);
				break;
			case "remove":
				if (! sender.hasPermission("jokes.remove")) {
					noPermission(sender, "jokes.remove");
					return;
				}
				// If you don't check and it passes null will throw exception
				if (numberOrText == null) {
					sender.sendMessage(ChatColor.RED + "You need to specify what to remove:");
					sendList(sender, jokeFile);
					return;
				}
				int number = Integer.parseInt(numberOrText);
				removeFile(sender, jokeFile, "jokes", number);
				sendList(sender, jokeFile);
		}

	}

	private static boolean userOverLimit (String uuid, Yaml file, String type) {
		// Ignore limit for console
		if (uuid.equals("console"))
			return false;
		return (file.getStringList(uuid).size() >= plugin.getCfg().getInt("user-limit." + type));
	}

	@CommandAlias ("quote|quotes")
	private static void onQuote (CommandSender sender, @Default ("send") String action, @Optional String numberOrText) {
		Yaml quoteFile = plugin.getQuoteFile();
		List <String> quoteList = quoteFile.getStringList("quotes");
		switch (action) {
			case "send":
				sendRandom(sender, quoteList);
				break;
			case "add":
				addFile(sender, quoteFile, "quotes", numberOrText);
			case "list":
				sendList(sender, quoteFile);
				break;
			case "remove":
				int number = Integer.parseInt(numberOrText);
				removeFile(sender, quoteFile, "quotes", number);
				sendList(sender, quoteFile);
		}
	}

	private static void addFile (CommandSender sender, Yaml file, String key, String text) {
		List <String> list = file.getStringList(key);
		list.add(text);
		file.set(key, list);
		file.write();
		sender.sendMessage(text + " has been added to " + key);
	}

	private static void sendList (CommandSender sender, Yaml file) {
		HashMap <String, List <String>> map = new HashMap <>();
		String playerName;
		int i = 0;
		for (String key : file.keySet()) {
			if (key.equalsIgnoreCase("console"))
				playerName = "Server";
			else
				playerName = Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
//			playerName = (key.equals("console")) ? "Server" : Bukkit.getOfflinePlayer(UUID.fromString(key)).getName();
			sender.sendMessage(playerName + " ");

			for (String joke : file.getStringList(key)) {
				i++;
				joke = ChatColor.translateAlternateColorCodes('&', joke);
				String message = i + " " + joke;
				sender.sendMessage(message);
			}
		}
		;
		//		for (String key : file.keySet()) {
		//			String userName = key.equals("console") ? "console" : Bukkit.getOfflinePlayer(key).getName();
		//			map.put(userName, file.getStringList(key));
		//		}
		//		String message = map.toString();
		//		ChatColor.translateAlternateColorCodes('&', message);
		//		sender.sendMessage(message);
/*
		for (int i = 0; i < list.size(); i++) {
			String message = ChatColor.translateAlternateColorCodes('&', list.get(i));
			sender.sendMessage((i + 1) + ": " + message);
		}
*/

	}

	private static void removeFile (CommandSender sender, Yaml file, String key, int remove) {
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
		String message = ChatColor.translateAlternateColorCodes('&', list.get(choice));
		sender.sendMessage(message);
	}

	// Currently unused - Found that last String of command will catch everything that comes after it.
	private static String joinString (String[] array) {
		CharSequence separator = " ";
		StringJoiner joiner = new StringJoiner(separator);
		for (String item : array) {
			joiner.add(item);
		}
		return joiner.toString();
	}

	private static void noPermission (CommandSender sender, String permission) {
		String message = "&3You do not have the needed permission: &f" + permission;
		message = ChatColor.translateAlternateColorCodes('&', message);
		sender.sendMessage(message);
	}

	@HelpCommand // Leave this for later
	private static void onHelp (CommandSender sender, CommandHelp help) {
		help.showHelp();
	}
}

