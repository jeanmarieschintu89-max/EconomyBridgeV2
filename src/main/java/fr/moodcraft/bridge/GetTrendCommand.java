package fr.moodcraft.bridge;

import org.bukkit.command.*;

public class GetTrendCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /trend <item>");
            return true;
        }

        String item = args[0];
        sender.sendMessage("§eTrend: " + TrendManager.getTrend(item));

        return true;
    }
}