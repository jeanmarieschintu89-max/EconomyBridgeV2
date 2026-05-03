package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MarketItemListHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ItemStack item = p.getOpenInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        String name = item.getItemMeta() != null 
                ? item.getItemMeta().getDisplayName() 
                : null;

        if (name == null) return;

        String clean = name.replaceAll("§.", "").toLowerCase();

        // 👉 Exemple: ouvrir GUI item
        MarketItemGUI.open(p, clean);
    }
}