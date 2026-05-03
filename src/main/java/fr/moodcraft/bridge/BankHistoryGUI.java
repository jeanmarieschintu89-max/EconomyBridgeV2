package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class BankHistoryGUI {

    private static final Map<UUID, Integer> pages = new HashMap<>();

    public static void open(Player p, int page) {

        pages.put(p.getUniqueId(), page);

        Inventory inv = Bukkit.createInventory(null, 54, "§dHistorique bancaire");

        List<String> logs = new ArrayList<>(BankStorage.getLogs());
        Collections.reverse(logs); // 🔥 plus récent en premier

        int start = page * 45;
        int end = Math.min(start + 45, logs.size());

        int slot = 0;

        for (int i = start; i < end; i++) {

            String key = logs.get(i);

            String type = BankStorage.getLog(key, "type");
            String amount = BankStorage.getLog(key, "amount");
            String target = BankStorage.getLog(key, "target");

            SafeGUI.safeSet(inv, slot, SafeGUI.item(
                    Material.PAPER,
                    "§e" + type,
                    "§8────────────",
                    "§7Montant: §6" + amount + "€",
                    "§7Cible: §f" + target
            ));

            slot++;
        }

        // ⬅ page précédente
        if (page > 0) {
            SafeGUI.safeSet(inv, 45, SafeGUI.item(Material.ARROW, "§e⬅ Précédent"));
        }

        // ➡ page suivante
        if (end < logs.size()) {
            SafeGUI.safeSet(inv, 53, SafeGUI.item(Material.ARROW, "§eSuivant ➡"));
        }

        // 🔙 retour
        SafeGUI.safeSet(inv, 49, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "bank_history", inv);
    }

    public static int getPage(Player p) {
        return pages.getOrDefault(p.getUniqueId(), 0);
    }
}