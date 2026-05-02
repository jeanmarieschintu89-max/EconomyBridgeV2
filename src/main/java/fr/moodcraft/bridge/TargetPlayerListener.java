package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TargetPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (e.getView().getTitle() == null || !e.getView().getTitle().contains("Choisir un joueur")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;

        String name = e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "");

        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // 🔥 STOCKAGE TEMPORAIRE
        ContractManager.target.put(p.getUniqueId(), target.getUniqueId());

        p.sendMessage("§aCible sélectionnée: §e" + target.getName());
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        p.closeInventory();
    }
}