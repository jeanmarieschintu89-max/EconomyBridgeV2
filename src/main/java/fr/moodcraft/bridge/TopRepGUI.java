package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class TopRepGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6🏆 Classement Réputation");

        int slot = 10;
        int i = 1;

        String viewerUUID = p.getUniqueId().toString();

        for (Map.Entry<String, Integer> entry : ReputationManager.getTop(10).entrySet()) {

            if (slot >= inv.getSize()) break;

            String uuidStr = entry.getKey();
            int rep = entry.getValue();

            UUID uuid;

            try {
                uuid = UUID.fromString(uuidStr);
            } catch (Exception e) {
                continue;
            }

            String name = Bukkit.getOfflinePlayer(uuid).getName();
            if (name == null) name = "Inconnu";

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);

            if (item.getItemMeta() instanceof SkullMeta meta) {

                meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));

                // 🎨 couleurs podium
                String prefix = switch (i) {
                    case 1 -> "§6👑 ";
                    case 2 -> "§7🥈 ";
                    case 3 -> "§c🥉 ";
                    default -> "§e#" + i + " ";
                };

                // ⭐ highlight du joueur
                boolean isSelf = uuidStr.equals(viewerUUID);

                meta.setDisplayName(prefix + "§f" + name + (isSelf ? " §a(TOI)" : ""));

                meta.setLore(java.util.Arrays.asList(
                        "§8────────────",
                        "§7Réputation: §a" + rep,
                        "§7Statut: " + ReputationManager.getRank(rep),
                        "",
                        isSelf ? "§a✔ Ta position actuelle" : "§7Joueur classé",
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

        // 🔳 bordures stylées
        SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);

        // 📊 info joueur en bas
        int myRep = ReputationManager.get(viewerUUID);
        int pos = ReputationManager.getPosition(viewerUUID);

        SafeGUI.safeSet(inv, 22, SafeGUI.item(
                Material.EMERALD,
                "§a📊 Ton classement",
                "§8────────────",
                "§7Position: §e#" + (pos == -1 ? "?" : pos),
                "§7Réputation: §a" + myRep,
                "",
                "§7Statut: " + ReputationManager.getRank(myRep)
        ));

        // ❌ fermer
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cFermer"));

        GUIManager.open(p, "top_rep", inv);
    }
}