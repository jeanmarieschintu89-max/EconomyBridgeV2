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

        int slot = 0;

        for (int i = logs.size() - 1; i >= 0 && slot < 21; i--) {

            try {
                String[] parts = logs.get(i).split("\\|\\|");
                String date = parts[0];
                String[] data = parts[1].split("\\|");

                String type = data[0];
                String item = data.length > 1 ? data[1] : "paper";
                double total = data.length > 4 ? Double.parseDouble(data[4]) : 0;

                Material mat;
                try {
                    mat = Material.valueOf(item.toUpperCase());
                } catch (Exception e) {
                    mat = Material.PAPER;
                }

                SafeGUI.safeSet(inv, slot,
                        SafeGUI.item(mat,
                                (type.equalsIgnoreCase("VENTE") ? "§a" : "§c") + type,
                                "§7" + item,
                                "§6" + df.format(total) + "€",
                                "§8" + date));

                slot++;

            } catch (Exception ignored) {}
        }

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}