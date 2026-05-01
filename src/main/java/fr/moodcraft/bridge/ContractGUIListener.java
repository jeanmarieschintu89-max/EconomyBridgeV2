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

        if (!e.getView().getTitle().equals("§6📄 Contrats")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        int slot = e.getSlot();

        // ➕ CREATION
        if (slot == 49) {
            p.closeInventory();
            ContractCreateGUI.open(p);
            return;
        }

        // 🔁 reconstruire liste
        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {
            var c = ContractManager.contracts.get(id);
            if (c != null && c.to.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        // 📌 calcul index
        int baseSlot = slot % 9;
        int row = slot / 9;

        if (baseSlot >= list.size()) return;

        UUID id = list.get(baseSlot);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

        // ✔ ACCEPTER (ligne 2)
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

        // ❌ REFUSER (ligne 3)
        if (row == 2) {

            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);

            p.sendMessage("§c❌ Contrat refusé");
        }

        ContractGUI.open(p);
    }
}