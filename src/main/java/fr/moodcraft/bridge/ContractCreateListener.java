package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6✏ Création contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var b = ContractCreateGUI.builders.get(p.getUniqueId());
        if (b == null) return;

        switch (e.getSlot()) {

            // 👤 joueur
            case 10 -> {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    b.target = Bukkit.getOnlinePlayers().iterator().next().getName();
                    p.sendMessage("§aJoueur sélectionné: " + b.target);
                }
            }

            // 📦 item
            case 11 -> {
                var item = p.getInventory().getItemInMainHand();
                if (item.getType() != Material.AIR) {
                    b.item = item.getType().name().toLowerCase();
                    p.sendMessage("§aItem: " + b.item);
                }
            }

            // 🔢 quantité
            case 12 -> {
                if (e.isLeftClick()) b.amount++;
                if (e.isRightClick() && b.amount > 1) b.amount--;
            }

            // 💰 prix
            case 13 -> {
                if (e.isLeftClick()) b.price += 100;
                if (e.isRightClick() && b.price > 100) b.price -= 100;
            }

            // ✔ valider
            case 16 -> {

                if (b.target == null || b.item == null) {
                    p.sendMessage("§c❌ Remplis tout");
                    return;
                }

                UUID id = UUID.randomUUID();

                ContractManager.Contract c = new ContractManager.Contract(
                        p.getName(),
                        b.target,
                        b.item,
                        b.amount,
                        b.price
                );

                ContractManager.contracts.put(id, c);

                p.sendMessage("§a✔ Contrat créé");

                p.closeInventory();
            }
        }

        ContractCreateGUI.open(p);
    }
}