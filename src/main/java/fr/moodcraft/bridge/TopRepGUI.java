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

        System.out.println("[TopRepGUI] Ouverture GUI");

        Inventory inv = Bukkit.createInventory(null, 27, "§6Classement Réputation");

        int slot = 10;
        int i = 1;

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            if (slot >= inv.getSize()) break; // 🔥 sécurité

            String uuidStr = entry.getKey();
            int rep = entry.getValue();

            UUID uuid;

            try {
                uuid = UUID.fromString(uuidStr);
            } catch (Exception e) {
                System.out.println("[TopRepGUI] UUID invalide: " + uuidStr);
                continue; // 🔥 skip au lieu de crash
            }

            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);

            if (item.getItemMeta() instanceof SkullMeta meta) {

                meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));

                meta.setDisplayName("§e#" + i + " §f" + name);

                meta.setLore(java.util.Arrays.asList(
                        "§7Réputation: §a" + rep,
                        "§7Statut: " + ReputationManager.getRank(rep),
                        "",
                        "§e▶ Voir profil"
                ));

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

        // 🔳 bordures
        SafeGUI.fillBorders(inv, Material.BLACK_STAINED_GLASS_PANE);

        // 🔥 ouverture
        GUIManager.open(p, "top_rep", inv);
    }
}