package fr.moodcraft.bridge;

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

        // 🔒 Vérifie GUI
        if (!e.getView().getTitle().equals("§6📄 Contrats")) return;

        // 🔒 Clique uniquement dans le menu
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getSlot();

        // =========================
        // ➕ CREER CONTRAT
        // =========================
        if (slot == 22) {
            p.closeInventory();
            p.sendMessage("§e✏ Création de contrat:");
            p.sendMessage("§7/contrat create <joueur> <item> <quantité> <prix>");
            return;
        }

        // =========================
        // 📄 LISTE CONTRATS
        // =========================
        List<UUID> list = new ArrayList<>();

        for (UUID id : ContractManager.contracts.keySet()) {
            var c = ContractManager.contracts.get(id);
            if (c != null && c.to.equalsIgnoreCase(p.getName())) {
                list.add(id);
            }
        }

        if (slot < 0 || slot >= list.size()) return;

        UUID id = list.get(slot);
        var c = ContractManager.contracts.get(id);

        if (c == null) return;

        // ✔ ACCEPT
        if (e.isLeftClick()) {
            c.accepted = true;
            p.sendMessage("§a✔ Contrat accepté");
        }

        // ❌ REFUSE
        if (e.isRightClick()) {
            ContractManager.contracts.remove(id);
            ReputationManager.add(c.from, -1);
            p.sendMessage("§c❌ Contrat refusé");
        }

        ContractGUI.open(p);
    }
}