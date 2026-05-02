package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;
import java.util.List;

public class BankHistoryGUI {

    public static void open(Player p, int page) {

        List<String> logs = TransactionLogger.getAll(p.getName());
        Inventory inv = Bukkit.createInventory(null, 27, "§6Historique");

        if (logs == null || logs.isEmpty()) {
            SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.BARRIER, "§cVide"));
            p.openInventory(inv);
            return;
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");

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

                // 🎨 couleurs + signes
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

                // 🎯 icône
                Material mat = Material.PAPER;

                if (type.contains("Vente")) mat = Material.EMERALD;
                else if (type.contains("Achat")) mat = Material.REDSTONE;
                else if (type.contains("Depot")) mat = Material.GOLD_INGOT;
                else if (type.contains("Retrait")) mat = Material.IRON_INGOT;

                SafeGUI.safeSet(inv, slot,
                        SafeGUI.item(mat,
                                "§eTransaction",
                                "§8────────",
                                "§7Type: §f" + type,
                                "§7Montant: " + color + sign + df.format(amount) + "€",
                                "",
                                "§7Date:",
                                "§f" + date,
                                "§8────────"
                        ));

                slot++;

            } catch (Exception ignored) {}
        }

        // =========================
        // 🔁 NAVIGATION
        // =========================

        // page précédente
        if (page > 0) {
            SafeGUI.safeSet(inv, 21,
                    SafeGUI.item(Material.ARROW, "§aPage précédente"));
        }

        // page suivante
        if (start - perPage >= 0) {
            SafeGUI.safeSet(inv, 23,
                    SafeGUI.item(Material.ARROW, "§aPage suivante"));
        }

        // retour
        SafeGUI.safeSet(inv, 22,
                SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}