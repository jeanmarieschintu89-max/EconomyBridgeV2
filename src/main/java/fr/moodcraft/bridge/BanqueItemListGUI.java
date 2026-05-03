package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BanqueItemListGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§bItems Marché");

        int slot = 0;

        for (String item : MarketState.base.keySet()) {

            double price = MarketState.base.get(item);

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.DIAMOND,
                            "§b" + item,
                            "§7Prix: §f" + price + "€",
                            "",
                            "§aClique pour modifier"));

            slot++;
            if (slot >= 45) break;
        }

        for (int i = 45; i < 54; i++) {
            SafeGUI.safeSet(inv, i,
                    SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        SafeGUI.safeSet(inv, 49,
                SafeGUI.item(Material.ARROW, "§cRetour"));

        p.openInventory(inv);
    }
}