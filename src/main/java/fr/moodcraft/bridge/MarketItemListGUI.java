package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MarketItemListGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§bItems Marché");

        int slot = 0;

        for (String item : MarketState.base.keySet()) {

            if (slot >= 54) break; // 🔒 sécurité

            double price = MarketState.base.get(item);

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.DIAMOND,
                            "§b" + item,
                            "§8────────────",
                            "§7Prix de base",
                            "",
                            "§f" + price + "€",
                            "",
                            "§aClique pour modifier"));

            slot++;
        }

        p.openInventory(inv);
    }
}