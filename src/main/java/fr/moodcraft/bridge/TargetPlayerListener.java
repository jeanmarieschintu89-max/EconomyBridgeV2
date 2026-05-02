package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TargetPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        if (title == null || !title.contains("Choisir joueur")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;

        String name = e.getCurrentItem().getItemMeta().getDisplayName();
        if (name == null) return;

        // 🔥 Nettoyage couleur
        name = name.replace("§a", "").replace("§f", "");

        Player target = Bukkit.getPlayerExact(name);

        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // ✅ UTILISE ContractBuilder (CORRECT)
        var builder = ContractBuilder.get(p);
        builder.target = target.getName();

        p.sendMessage("§aJoueur sélectionné: §e" + target.getName());

        // 🔄 retour menu création
        ContractCreateGUI.open(p);
    }
}