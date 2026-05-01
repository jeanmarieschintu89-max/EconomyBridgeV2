package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class BankHistoryGUI {

    public static void open(Player p, int page) {

        List<String> logs = TransactionLogger.getAll(p.getName());

        Inventory inv = Bukkit.createInventory(null, 27, "§6📄 Historique");

        if (logs == null || logs.isEmpty()) {
            inv.setItem(13, ItemBuilder.of(Material.BARRIER, "§cAucune transaction"));
            p.openInventory(inv);
            return;
        }

        int slot = 0;

        for (int i = logs.size() - 1; i >= 0 && slot < 21; i--) {
            inv.setItem(slot, ItemBuilder.of(Material.PAPER, "§7Transaction", logs.get(i)));
            slot++;
        }

        inv.setItem(22, ItemBuilder.of(Material.BARRIER, "§cRetour"));

        p.openInventory(inv);
    }
}