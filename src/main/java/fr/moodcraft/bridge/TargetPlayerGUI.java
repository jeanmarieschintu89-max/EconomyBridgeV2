package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TargetPlayerGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§eChoisir un joueur");

        int slot = 0;

        for (Player target : Bukkit.getOnlinePlayers()) {

            if (target.equals(p)) continue;

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.PLAYER_HEAD,
                            "§a" + target.getName(),
                            "§8────────────",
                            "§7Cliquer pour sélectionner"));

            slot++;
        }

        p.openInventory(inv);
    }
}