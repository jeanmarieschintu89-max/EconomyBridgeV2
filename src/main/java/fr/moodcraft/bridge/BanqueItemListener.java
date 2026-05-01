package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BanqueItemListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getItem() == null) return;

        ItemStack item = e.getItem();

        if (item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().hasDisplayName()) return;

        String name = item.getItemMeta().getDisplayName();

        // =========================
        // 💳 CARTE BANCAIRE
        // =========================
        if (name.equalsIgnoreCase("§b💳 Carte bancaire")) {

            e.setCancelled(true);

            BankGUI.open(p);

            p.playSound(p.getLocation(), "ui.button.click", 1f, 1f);
        }

        // =========================
        // 📤 IBAN
        // =========================
        if (name.equalsIgnoreCase("§e📤 IBAN")) {

            e.setCancelled(true);

            String iban = BankStorage.getIban(p.getUniqueId().toString());

            p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
            p.sendMessage("§e🏦 Banque MoodCraft");
            p.sendMessage("§7Titulaire: §e" + p.getName());
            p.sendMessage("§7IBAN: §b" + iban);
            p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
        }
    }
}