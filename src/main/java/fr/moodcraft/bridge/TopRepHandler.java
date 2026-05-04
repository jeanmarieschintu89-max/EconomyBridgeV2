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

        // 🔒 sécurité slot
        if (slot < 0 || slot >= 27) return;

        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        // 🔒 ignore tout sauf les têtes
        if (item == null || item.getType().isAir()) return;
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // 🔒 vérifie qu’on a bien une cible
        String uuidStr = meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), "target"),
                PersistentDataType.STRING
        );

        if (uuidStr == null) return; // ← très important

        UUID targetUUID = UUID.fromString(uuidStr);

        // 🔥 ouverture safe (évite bugs inventaire)
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            ProfileGUI.open(p, targetUUID);
        });

        // ❌ optionnel → tu peux enlever
        // p.sendMessage("§6➜ §7Ouverture du profil...");
    }
}