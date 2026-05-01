package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class BankHistoryGUI {

    private static final int ITEMS_PER_PAGE = 21;

    public static void open(Player p, int page) {

        List<String> logs = TransactionLogger.getAll(p.getName());

        Inventory inv = Bukkit.createInventory(null, 27, "§6📄 Historique");

        if (logs == null || logs.isEmpty()) {
            inv.setItem(13, ItemBuilder.of(Material.BARRIER, "§cAucune transaction"));
            p.openInventory(inv);
            return;
        }

        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, logs.size());

        int slot = 0;

        for (int i = start; i < end; i++) {
            inv.setItem(slot, ItemBuilder.of(Material.PAPER, "§7Transaction",
                    "§f" + logs.get(i)));
            slot++;
        }

        // ⬅ page précédente
        if (page > 0) {
            inv.setItem(21, ItemBuilder.of(Material.ARROW, "§aPage précédente"));
        }

        // ➡ page suivante
        if (end < logs.size()) {
            inv.setItem(23, ItemBuilder.of(Material.ARROW, "§aPage suivante"));
        }

        // 🔙 retour
        inv.setItem(22, ItemBuilder.of(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}