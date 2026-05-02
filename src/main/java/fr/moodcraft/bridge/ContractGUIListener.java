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

        if (!e.getView().getTitle().equals("§6Contrats")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getSlot();

        // ➕ CREATION
        if (slot == 22) {
            p.closeInventory();
            ContractCreateGUI.open(p);
            return;
        }

        // 🔁 liste contrats
        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {
            var c = ContractManager.contracts.get(id);
            if (c != null && c.to.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (list.isEmpty()) return;

        int index = slot;

        if (index < 0 || index >= list.size()) return;

        UUID id = list.get(index);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

        // ✔ ACCEPTER
        if (e.isLeftClick()) {

            c.accepted = true;

            var book = ContractItem.create(id, c);

            p.getInventory().addItem(book.clone());

            Player from = Bukkit.getPlayerExact(c.from);
            if (from != null) {
                from.getInventory().addItem(book.clone());
            }

            p.sendMessage("§a✔ Contrat accepté");
        }

        // ❌ REFUSER
        if (e.isRightClick()) {

            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);

            p.sendMessage("§c❌ Contrat refusé");
        }

        ContractGUI.open(p);
    }
}