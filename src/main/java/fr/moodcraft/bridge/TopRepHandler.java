package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TopRepHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        if (slot < 0 || slot >= 27) return;

        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        if (item == null || item.getType().isAir()) return;
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        String uuidStr = meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), "target"),
                PersistentDataType.STRING
        );

        if (uuidStr == null) return;

        UUID targetUUID = UUID.fromString(uuidStr);

        // 🔥 FIX ICI
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            ProfileGUI.open(p, targetUUID);
        });

        p.sendMessage("§6➜ §7Ouverture du profil...");
    }
}