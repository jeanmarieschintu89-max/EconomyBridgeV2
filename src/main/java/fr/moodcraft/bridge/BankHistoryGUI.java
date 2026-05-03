package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankHistoryGUI {

    // 🔥 stockage des pages par joueur
    private static final Map<Player, Integer> pages = new HashMap<>();

    public static void open(Player p, int page) {

        pages.put(p, page); // 🔥 sauvegarde page

        List<String> logs = TransactionLogger.getAll(p.getName());
        Inventory inv = Bukkit.createInventory(null, 27, "§fHistorique");

        // ❌ vide
        if (logs == null || logs.isEmpty()) {
            SafeGUI.safeSet(inv, 13,
                    SafeGUI.item(Material.BARRIER, "§cVide"));

            GUIManager.open(p, "bank_history", inv);
            return;
        }

        int perPage = 21;
        int start = logs.size() - (page * perPage) - 1;
        int end = Math.max(start - perPage + 1, 0);

        int slot = 0;

        for (int i = start; i >= end && slot < 21; i--) {

            try {

                String[] parts = logs.get(i).split("\\|\\|");

                String date = parts.length > 0 ? parts[0] : "??";
                String type = parts.length > 1 ? parts[1] : "Inconnu";

                double amount = 0;
                try {
                    amount = parts.length > 2 ? Double.parseDouble(parts[2]) : 0;
                } catch (Exception ignored) {}

                String color = "§7";
                String sign = "";

                if (type.contains("Vente")) {
                    color = "§a";
                    sign = "+";
                } else if (type.contains("Depot")) {
                    color = "§b";
                    sign = "+";
                } else if (type.contains("Retrait")) {
                    color = "§e";
                    sign = "-";
                } else if (type.contains("Achat")) {
                    color = "§c";
                    sign = "-";
                }

                Material mat = Material.PAPER;

                if (type.contains("Vente")) mat = Material.EMERALD;
                else if (type.contains("Achat")) mat = Material.REDSTONE;
                else if (type.contains("Depot")) mat = Material.GOLD_INGOT;
                else if (type.contains("Retrait")) mat = Material.IRON_INGOT;

                SafeGUI.safeSet(inv, slot,
                        SafeGUI.item(mat,
                                "§eTransaction",
                                "§8────────────",
                                "§7Type: §f" + type,
                                "§7Montant: " + color + sign + SafeGUI.money(amount),
                                "",
                                "§7Date:",
                                "§f" + date,
                                "§8────────────"
                        ));

                slot++;

            } catch (Exception ignored) {}
        }

        // ⬅ précédent
        if (page > 0) {
            SafeGUI.safeSet(inv, 21,
                    SafeGUI.item(Material.ARROW, "§a⬅ Page précédente"));
        }

        // ➡ suivant
        if (start - perPage >= 0) {
            SafeGUI.safeSet(inv, 23,
                    SafeGUI.item(Material.ARROW, "§aPage suivante ➡"));
        }

        // 🔙 retour
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§c⬅ Retour"));

        // 🔥 ouverture via GUIManager
        GUIManager.open(p, "bank_history", inv);
    }

    // 🔥 récupérer page actuelle
    public static int getPage(Player p) {
        return pages.getOrDefault(p, 0);
    }
}