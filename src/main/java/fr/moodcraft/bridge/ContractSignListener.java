package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ContractSignListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        // 🔒 évite double trigger (main/offhand)
        if (e.getHand() != EquipmentSlot.HAND) return;

        // 🔒 uniquement clic droit
        if (e.getAction() != Action.RIGHT_CLICK_AIR &&
            e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null || item.getType() != Material.WRITTEN_BOOK) return;
        if (!item.hasItemMeta()) return;

        var meta = item.getItemMeta();
        if (meta.getLore() == null) return;

        for (String line : meta.getLore()) {

            if (line == null || !line.startsWith("§8ID: ")) continue;

            String idStr = line.replace("§8ID: ", "").trim();

            UUID id;
            try {
                id = UUID.fromString(idStr);
            } catch (Exception ex) {
                continue; // 🔥 skip au lieu de return
            }

            var c = ContractManager.contracts.get(id);
            if (c == null) continue;

            // 🔒 bon joueur uniquement
            if (!c.to.equalsIgnoreCase(p.getName())) {
                p.sendMessage("§cCe contrat ne t'est pas destiné");
                e.setCancelled(true);
                return;
            }

            // 🔒 déjà signé ?
            if (c.signed) {
                p.sendMessage("§eContrat déjà signé");
                e.setCancelled(true);
                return;
            }

            // ✔ signature
            c.signed = true;

            p.sendMessage("§a✔ Contrat signé");

            // 🔒 empêche ouverture du livre
            e.setCancelled(true);

            return;
        }
    }
}