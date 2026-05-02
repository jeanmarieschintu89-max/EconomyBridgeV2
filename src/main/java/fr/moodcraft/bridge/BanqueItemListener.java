package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BanqueItemListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        ItemStack item = e.getItem();
        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        String name = item.getItemMeta().getDisplayName();

        // =========================
        // 💳 CARTE BANCAIRE
        // =========================
        if (name.contains("Carte")) {

            e.setCancelled(true);

            BankGUI.open(p);

            // 🔥 SON SAFE
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        }

        // =========================
        // 📤 IBAN
        // =========================
        else if (name.contains("IBAN")) {

            e.setCancelled(true);

            String iban = BankStorage.getIban(p.getUniqueId().toString());

            p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
            p.sendMessage("§eBanque MoodCraft");
            p.sendMessage("§7Titulaire: §e" + p.getName());
            p.sendMessage("§7IBAN: §b" + iban);
            p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
        }
    }
}