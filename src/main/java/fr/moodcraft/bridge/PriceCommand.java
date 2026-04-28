package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) return false;

        String item = args[0];

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            PriceUpdater.updateItem(item);
        });

        return true;
    }
}