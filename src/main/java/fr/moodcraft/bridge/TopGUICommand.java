package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopGUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande uniquement en jeu");
            return true;
        }

        // 🔥 feedback sonore (optionnel mais stylé)
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        p.sendMessage("§aDEBUG: /topgui OK");
TopRepGUI.open(p);

        // 🔥 ouverture GUI
        TopRepGUI.open(p);

        return true;
    }
}