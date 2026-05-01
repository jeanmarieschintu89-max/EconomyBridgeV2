package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ContractSignListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getItem() == null) return;

        ItemStack item = e.getItem();

        if (item.getType() != Material.WRITTEN_BOOK) return;
        if (!item.hasItemMeta()) return;

        var meta = item.getItemMeta();

        if (meta.getLore() == null) return;

        for (String line : meta.getLore()) {

            if (line.startsWith("§8ID: ")) {

                String idStr = line.replace("§8ID: ", "");

                UUID id;
                try {
                    id = UUID.fromString(idStr);
                } catch (Exception ex) {
                    return;
                }

                var c = ContractManager.contracts.get(id);
                if (c == null) return;

                // 🔒 vérifie que c’est le bon joueur
                if (!c.to.equalsIgnoreCase(p.getName())) return;

                // ✔ signature
                c.signed = true;

                p.sendMessage("§a✍ Contrat signé");

                return;
            }
        }
    }
}