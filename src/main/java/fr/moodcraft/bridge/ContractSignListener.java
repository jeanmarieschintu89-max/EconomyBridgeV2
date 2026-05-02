package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ContractSignListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        // 🔒 évite double déclenchement (main/off-hand)
        if (e.getHand() != EquipmentSlot.HAND) return;

        Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null) return;

        if (item.getType() != Material.WRITTEN_BOOK) return;
        if (!item.hasItemMeta()) return;

        var meta = item.getItemMeta();
        if (meta.getLore() == null) return;

        for (String line : meta.getLore()) {

            if (!line.startsWith("§8ID: ")) continue;

            String idStr = line.replace("§8ID: ", "");

            UUID id;
            try {
                id = UUID.fromString(idStr);
            } catch (Exception ex) {
                return;
            }

            var c = ContractManager.contracts.get(id);
            if (c == null) return;

            // 🔒 bon joueur uniquement
            if (!c.to.equalsIgnoreCase(p.getName())) {
                p.sendMessage("§cCe contrat ne t'est pas destiné");
                return;
            }

            // 🔒 déjà signé ?
            if (c.signed) {
                p.sendMessage("§eContrat deja signe");
                return;
            }

            // ✔ signature
            c.signed = true;

            p.sendMessage("§aContrat signe");

            // 🔒 empêche l'ouverture du livre
            e.setCancelled(true);

            return;
        }
    }
}