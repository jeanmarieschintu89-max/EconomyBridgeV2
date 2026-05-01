package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6📄 Contrats")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        int slot = e.getSlot();

        int index = 0;

        for (UUID id : ContractManager.contracts.keySet()) {

            var c = ContractManager.contracts.get(id);

            if (!c.to.equalsIgnoreCase(p.getName())) continue;

            if (index == slot) {

                if (e.isLeftClick()) {
                    c.accepted = true;
                    p.sendMessage("§a✔ Contrat accepté");
                }

                if (e.isRightClick()) {
                    ContractManager.contracts.remove(id);
                    ReputationManager.add(c.from, -1);
                    p.sendMessage("§c❌ Contrat refusé");
                }

                ContractGUI.open(p);
                return;
            }

            index++;
        }
    }
}