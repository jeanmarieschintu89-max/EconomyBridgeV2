package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

public class TopRepGUI {

    public static void open(org.bukkit.entity.Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Classement Réputation");

        int slot = 10;
        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            String uuidStr = entry.getKey();
            int rep = entry.getValue();

            UUID uuid = UUID.fromString(uuidStr);

            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);

            if (item.getItemMeta() instanceof SkullMeta meta) {

                // 🔥 skin du joueur
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));

                meta.setDisplayName("§e#" + i + " §f" + name);

                meta.setLore(java.util.Arrays.asList(
                        "§7Réputation: §a" + rep,
                        "§7Statut: " + ReputationManager.getRank(rep),
                        "",
                        "§e▶ Voir profil"
                ));

                // 🔥 stock UUID (IMPORTANT pour le handler)
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(Main.getInstance(), "target"),
                        PersistentDataType.STRING,
                        uuidStr
                );

                item.setItemMeta(meta);
            }

            inv.setItem(slot, item);

            slot++;
            i++;
        }

        // ✨ bordures (optionnel mais recommandé)
        SafeGUI.fillBorders(inv, Material.BLACK_STAINED_GLASS_PANE);

        // 🔥 ouverture via GUIManager
        GUIManager.open(p, "top_rep", inv);
    }
}