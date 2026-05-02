package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION (anti couleurs / Bedrock)
        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Contrats")) return;

        if (e.getClickedInventory() == null) return;

        // 🔥 FIX CRITIQUE → ne bloque QUE le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        // =========================
        // ➕ CREATION
        // =========================
        if (slot == 49) {
            p.closeInventory();
            ContractCreateGUI.open(p);
            return;
        }

        // =========================
        // 🔁 RÉCUP LISTE CONTRATS
        // =========================
        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {
            var c = ContractManager.contracts.get(id);

            if (c == null) continue;

            if (c.to.equalsIgnoreCase(p.getName()) || c.from.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (list.isEmpty()) return;

        // 🔥 mapping propre slot → contrat
        int col = slot % 9;
        int row = slot / 9;

        if (col >= list.size()) return;

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

            p.sendMessage("§cContrat refusé");
        }

        // =========================
        // 🗑 ANNULER
        // =========================
        else if (row == 3) {

            ContractManager.contracts.remove(id);

            p.sendMessage("§cContrat annulé");
        }

        // 🔄 refresh GUI
        ContractGUI.open(p);
    }
}