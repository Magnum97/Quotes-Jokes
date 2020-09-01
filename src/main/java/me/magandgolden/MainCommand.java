package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings ("unused")
public class MainCommand extends BaseCommand {

	/* Moved all methods to DataWorks */

	private static final QuotesJokes plugin = QuotesJokes.getPlugin();

	@CommandAlias ("joke|jokes")
	// @Optional will return null if not provided in command. This needs to be checked before passing to methods
	private static void onJoke (CommandSender sender, @Default ("send") String action, @Optional String numberOrText) {
		Yaml jokeFile = plugin.getJokeFile();
		// Create new instnace of dataworks and tells it we are using jokeFile
		DataWorks dw = new DataWorks(jokeFile);
		String uuid = (sender instanceof Player) ? ((Player) sender).getUniqueId().toString() : "console";

		switch (action) {
			case "send":
				dw.sendRandom(sender); // Does not need to pass list or file bc it was passed to dw above.
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
				if (! dw.userOverLimit(uuid, "jokes")) // Check how many jokes a user has added
					dw.addFile(sender, uuid, numberOrText);
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
				dw.sendList(sender);
				break;
			case "remove":
				if (! sender.hasPermission("jokes.remove")) {
					noPermission(sender, "jokes.remove");
					return;
				}
				// If you don't check and it passes null will throw exception
				if (numberOrText == null) {
					sender.sendMessage(ChatColor.RED + "You need to specify what to remove:");
					dw.sendList(sender);
					return;
				}
				int number = Integer.parseInt(numberOrText);
				dw.removeFile(sender, number);
				dw.sendList(sender);
		}

	}


	@CommandAlias ("quote|quotes")
	private static void onQuote (CommandSender sender, @Default ("send") String action, @Optional String numberOrText) {
		Yaml quoteFile = plugin.getQuoteFile();
		DataWorks dw = new DataWorks(quoteFile);
		String uuid = (sender instanceof Player) ? ((Player) sender).getUniqueId().toString() : "console";
		switch (action) {
			case "send":
				dw.sendRandom(sender);
				break;
			case "add":
				dw.addFile(sender, uuid, numberOrText);
			case "list":
				dw.sendList(sender);
				break;
			case "remove":
				int number = Integer.parseInt(numberOrText);
				dw.removeFile(sender, number);
				dw.sendList(sender);
		}
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

