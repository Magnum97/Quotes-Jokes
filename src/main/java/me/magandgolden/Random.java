package me.magandgolden;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("random")
public class Random extends BaseCommand {

    @Subcommand("joke")
    private static void joke(CommandSender sender, String action, String randomname){
        DataWorks dw = new DataWorks();

    }

    @Subcommand("quote")
    private static void quote(CommandSender sender, String action, String randomname){

    }
}
