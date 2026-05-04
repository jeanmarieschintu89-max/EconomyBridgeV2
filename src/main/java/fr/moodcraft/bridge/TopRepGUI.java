package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

public class TopRepGUI {

    public static void open(org.bukkit.entity.Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Classement Réputation");

        int slot = 10;
        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            String uuid = entry.getKey();
            int rep = entry.getValue();

            String name = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName("§e#" + i + " §f" + name);

                meta.setLore(java.util.Arrays.asList(
                        "§7Réputation: §a" + rep,
                        "§7Statut: " + ReputationManager.getRank(rep),
                        "",
                        "§e▶ Voir profil"
                ));

                // 🔥 STOCK UUID
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(Main.getInstance(), "target"),
                        PersistentDataType.STRING,
                        uuid
                );

                item.setItemMeta(meta);
            }

            inv.setItem(slot, item);

            slot++;
            i++;
        }

        // ⚠️ IMPORTANT
        GUIManager.open(p, "top_rep", inv);
    }
}