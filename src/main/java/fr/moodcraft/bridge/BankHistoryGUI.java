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
        Inventory inv = Bukkit.createInventory(null, 27, "§6📊 Historique Trader");

        if (logs == null || logs.isEmpty()) {
            inv.setItem(13, ItemBuilder.of(Material.BARRIER, "§cAucune transaction"));
            p.openInventory(inv);
            return;
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");

        int slot = 0;

        for (int i = logs.size() - 1; i >= 0 && slot < 21; i--) {

            String line = logs.get(i);

            try {
                String[] parts = line.split("\\|\\|");

                String date = parts[0];
                String payload = parts[1];

                String[] data = payload.split("\\|");

                String type = data[0];
                String item = data.length > 1 ? data[1] : "unknown";
                int amount = data.length > 2 ? Integer.parseInt(data[2]) : 0;
                double unit = data.length > 3 ? Double.parseDouble(data[3]) : 0;
                double total = data.length > 4 ? Double.parseDouble(data[4]) : 0;

                Material mat;
                try {
                    mat = Material.valueOf(item.toUpperCase());
                } catch (Exception e) {
                    mat = Material.PAPER;
                }

                String color = type.equalsIgnoreCase("VENTE") ? "§a" : "§c";

                inv.setItem(slot, ItemBuilder.of(mat,
                        color + type + " §7(" + item + ")",
                        "§7Date: §f" + date,
                        "§7Quantité: §e" + amount,
                        "§7Prix total: §6" + df.format(total) + "€",
                        "§7Prix unitaire: §8" + df.format(unit) + "€"));

                slot++;

            } catch (Exception ignored) {}
        }

        inv.setItem(22, ItemBuilder.of(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}