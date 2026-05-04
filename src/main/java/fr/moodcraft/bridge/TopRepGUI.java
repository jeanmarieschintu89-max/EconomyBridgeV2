package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class TopRepGUI {

    public static void open(Player p) {

        // 🔊 SON À L'OUVERTURE
        p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);

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

                // 🎨 PODIUM
                String prefix = switch (i) {
                    case 1 -> "§6👑 ";
                    case 2 -> "§7🥈 ";
                    case 3 -> "§c🥉 ";
                    default -> "§e#" + i + " ";
                };

                // ⭐ SURBRILLANCE DU JOUEUR
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

        // 🔳 BORDURES
        SafeGUI.fillBorders(inv, Material.GRAY_STAINED_GLASS_PANE);

        // 📊 INFO JOUEUR
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

        // ❌ FERMER
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cFermer"));

        // 🔥 OUVERTURE GUI
        GUIManager.open(p, "top_rep", inv);
    }
}