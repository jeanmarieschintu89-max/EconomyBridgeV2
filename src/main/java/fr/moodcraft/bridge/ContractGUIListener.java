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

        // 🔥 FIX BEDROCK
        if (title == null || !title.contains("Contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getSlot();

        // =========================
        // ➕ CREATION (FIX)
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
            if (c != null && c.to.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (list.isEmpty()) return;

        // 🔥 FIX POSITION
        int baseSlot = slot % 9;
        int row = slot / 9;

        if (baseSlot >= list.size()) return;

        UUID id = list.get(baseSlot);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

        // =========================
        // ✔ ACCEPTER (ligne 2)
        // =========================
        if (row == 1) {

            c.accepted = true;

            var book = ContractItem.create(id, c);

            p.getInventory().addItem(book.clone());

            Player from = Bukkit.getPlayerExact(c.from);
            if (from != null) {
                from.getInventory().addItem(book.clone());
            }

            p.sendMessage("§aContrat accepte");
        }

        // =========================
        // ❌ REFUSER (ligne 3)
        // =========================
        if (row == 2) {

            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);

            p.sendMessage("§cContrat refuse");
        }

        // =========================
        // 🗑 ANNULER (ligne 4)
        // =========================
        if (row == 3) {

            ContractManager.contracts.remove(id);

            p.sendMessage("§cContrat annule");
        }

        ContractGUI.open(p);
    }
}