package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankHistoryGUI {

    private static final int ITEMS_PER_PAGE = 21;

    public static void open(Player p, int page, String filter) {

        List<String> logs = TransactionLogger.getAll(p.getName());

        if (logs == null) logs = new ArrayList<>();

        // 🔍 FILTRE
        if (filter != null && !filter.equalsIgnoreCase("ALL")) {
            String f = filter.toLowerCase();
            logs = logs.stream()
                    .filter(line -> line.toLowerCase().contains(f))
                    .collect(Collectors.toList());
        }

        Inventory inv = Bukkit.createInventory(null, 27,
                "§6📄 Historique §8(" + filter + ")");

        if (logs.isEmpty()) {
            inv.setItem(13, ItemBuilder.of(Material.BARRIER, "§cAucune transaction"));
            p.openInventory(inv);
            return;
        }

        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, logs.size());

        int slot = 0;

        for (int i = logs.size() - 1 - start; i >= logs.size() - end; i--) {
            if (i < 0) break;

            inv.setItem(slot, ItemBuilder.of(Material.PAPER, "§7Transaction",
                    logs.get(i)));
            slot++;
        }

        // ⬅ PAGE PRÉCÉDENTE
        if (page > 0) {
            inv.setItem(21, ItemBuilder.of(Material.ARROW, "§a⬅ Page précédente"));
        }

        // ➡ PAGE SUIVANTE
        if (end < logs.size()) {
            inv.setItem(23, ItemBuilder.of(Material.ARROW, "§aPage suivante ➡"));
        }

        // 🎛 FILTRES
        inv.setItem(18, ItemBuilder.of(Material.BOOK, "§eFiltre: Tout"));
        inv.setItem(19, ItemBuilder.of(Material.EMERALD, "§aFiltre: Dépôt"));
        inv.setItem(20, ItemBuilder.of(Material.REDSTONE, "§cFiltre: Retrait"));

        // 🔙 RETOUR
        inv.setItem(22, ItemBuilder.of(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}