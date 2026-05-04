package fr.moodcraft.bridge;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class TopRepHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // ❌ sécurité
        if (slot < 0 || slot >= 27) return;

        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        if (item == null || item.getType().isAir()) return;
        if (!item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // 🔥 récup UUID stocké
        String uuid = meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), "target"),
                PersistentDataType.STRING
        );

        if (uuid == null) return;

        // 🎯 ACTION
        p.closeInventory();

        // 👉 ouvre profil joueur
        ProfileGUI.open(p, uuid);

        // 💬 feedback stylé
        p.sendMessage("§6➜ §7Ouverture du profil...");
    }
}