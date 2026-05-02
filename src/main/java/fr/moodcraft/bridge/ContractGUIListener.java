package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 FIX SAFE Bedrock
        if (title == null || !title.contains("Contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getRawSlot(); // 🔥 IMPORTANT (évite bugs Bedrock)
        if (slot > 53) return;

        // =========================
        // ➕ CREATION
        // =========================
        if (slot == 49) {
            p.closeInventory();
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // 🔁 LISTE CONTRATS
        // =========================
        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {

            var c = ContractManager.contracts.get(id);
            if (c == null) continue;

            if (c.to.equalsIgnoreCase(p.getName()) || c.from.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (list.isEmpty()) {
            p.sendMessage("§7Aucun contrat disponible");
            return;
        }

        // =========================
        // 📍 CALCUL POSITION
        // =========================
        int col = slot % 9;
        int row = slot / 9;

        // 👉 on ignore colonnes hors zone
        if (col < 0 || col >= list.size()) return;

        UUID id = list.get(col);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

        // =========================
        // ✔ ACCEPTER
        // =========================
        if (row == 1) {

            c.accepted = true;

            var book = ContractItem.create(id, c);

            p.getInventory().addItem(book.clone());

            Player from = Bukkit.getPlayerExact(c.from);
            if (from != null) {
                from.getInventory().addItem(book.clone());
            }

            p.sendMessage("§a✔ Contrat accepté");
        }

        // =========================
        // ❌ REFUSER
        // =========================
        else if (row == 2) {

            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);

            p.sendMessage("§c❌ Contrat refusé");
        }

        // =========================
        // 🗑 ANNULER
        // =========================
        else if (row == 3) {

            ContractManager.contracts.remove(id);

            p.sendMessage("§cContrat annulé");
        }

        // 🔄 refresh
        ContractGUI.open(p);
    }
}