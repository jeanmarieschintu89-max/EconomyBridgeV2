package fr.moodcraft.bridge;

import org.bukkit.command.*;

public class SyncCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        for (String item : PriceUpdater.ALLOWED) {
            PriceUpdater.updateItem(item);
        }

        sender.sendMessage("§a✔ Sync effectué");
        return true;
    }
}