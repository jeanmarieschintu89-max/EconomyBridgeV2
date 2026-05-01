package fr.moodcraft.bridge.commands;

import fr.moodcraft.bridge.TrendManager;
import org.bukkit.command.*;

public class GetTrendCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) return false;

        String item = args[0];

        sender.sendMessage(TrendManager.getTrend(item));

        return true;
    }
}