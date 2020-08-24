package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias ("random")
public class MainCommand extends BaseCommand {

	@Subcommand ("joke")
	private static void onJoke (CommandSender sender, @Optional String action, @Optional String arg2) {
		sender.sendMessage("This is a joke. ^_^ Now you laugh.");
	}

	@Subcommand ("quote")
	private static void onQuote (CommandSender sender, @Optional String action, @Optional String arg2) {
		sender.sendMessage("Here's your quote: \"");
	}

/*
	@Default // Leave this for later
	private static void defaultCommand (CommandSender sender, String type, @Optional String action, @Optional String arg2) {

	}
*/
}
