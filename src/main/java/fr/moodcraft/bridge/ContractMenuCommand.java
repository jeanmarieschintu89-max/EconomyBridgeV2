package fr.moodcraft.bridge;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ContractMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        ContractGUI.open(p);
        return true;
    }
}