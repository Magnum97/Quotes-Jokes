package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CommandAlias("random")
public class Random extends BaseCommand {

    @Subcommand("joke")
    private static void joke(CommandSender sender, String action, String randomname){
        DataWorks dw = new DataWorks();
        List jokes = dw.getJokes();
        switch (action) {
            case "add":
                jokes.add(randomname);
                break;
            case "list":
                for (int i = 0; i < jokes.size(); i++) {
                    sender.sendMessage(i + ":" + jokes.get(i));
                }
                break;
            case "remove":
                jokes.remove(randomname);
                break;
            default:
                int i = ThreadLocalRandom.current().nextInt(jokes.size());
                sender.sendMessage(String.valueOf(jokes.get(i)));
        }
    }

    @Subcommand("quote")
    private static void quote(CommandSender sender, String action, String randomname){
        DataWorks dw = new DataWorks();
        List quotes = dw.getQuotes();
        switch (action){
            case "add":
                quotes.add(randomname);
                break;
            case "list":
                for (int i = 0; i< quotes.size(); i++){
                    sender.sendMessage(i+":"+quotes.get(i));
                }
                break;
            case "remove":
                quotes.remove(randomname);
                break;
            default:
                int i = ThreadLocalRandom.current().nextInt(quotes.size());
                sender.sendMessage(String.valueOf(quotes.get(i)));
        }
    }
}
