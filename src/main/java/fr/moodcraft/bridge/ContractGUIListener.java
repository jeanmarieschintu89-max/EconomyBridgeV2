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

        // 🔒 FIX STRICT
        if (title == null || !title.equals("§eContrats")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        if (slot == 49) {
            p.closeInventory();
            ContractCreateGUI.open(p);
            return;
        }

        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {
            var c = ContractManager.contracts.get(id);

            if (c == null) continue;

            if (c.to.equalsIgnoreCase(p.getName()) || c.from.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (list.isEmpty()) return;

        int col = slot % 9;
        int row = slot / 9;

        if (col >= list.size()) return;

        UUID id = list.get(col);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

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

        else if (row == 2) {

            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);

            p.sendMessage("§cContrat refusé");
        }

        else if (row == 3) {

            ContractManager.contracts.remove(id);

            p.sendMessage("§cContrat annulé");
        }

        ContractGUI.open(p);
    }
}