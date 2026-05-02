package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        ContractMarketGUI.open(p);
        return true;
    }
}