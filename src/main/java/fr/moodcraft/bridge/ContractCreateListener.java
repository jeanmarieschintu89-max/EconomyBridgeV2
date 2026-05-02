package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // ❗ important : top inventory uniquement
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        int slot = e.getRawSlot();

        ContractBuilder builder = ContractBuilder.get(p.getUniqueId());

        // =========================
        // 📦 SLOT ITEM (autorisé)
        // =========================
        if (slot == 10) {
            e.setCancelled(false);

            // récupérer l'item après dépôt
            p.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
                ItemStack item = e.getInventory().getItem(10);

                if (item != null && item.getType() != Material.AIR) {
                    builder.item = item.getType().name();
                    p.sendMessage("§aItem sélectionné: §f" + builder.item);
                }
            }, 1L);

            return;
        }

        // bloque le reste
        e.setCancelled(true);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // =========================
            // 📊 QUANTITÉ
            // =========================
            case 12 -> {
                builder.amount += 1;
                p.sendMessage("§bQuantité: §f" + builder.amount);
            }

            // =========================
            // 💰 PRIX
            // =========================
            case 14 -> {
                builder.price += 100;
                p.sendMessage("§6Prix: §f" + builder.price + "€");
            }

            // =========================
            // ✅ VALIDATION
            // =========================
            case 16 -> {

                if (builder.item == null) {
                    p.sendMessage("§c❌ Aucun item défini");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        builder.item,
                        builder.amount,
                        builder.price
                );

                p.sendMessage("§8────────────");
                p.sendMessage("§a✔ Contrat créé !");
                p.sendMessage("§7Item: §f" + builder.item);
                p.sendMessage("§7Quantité: §f" + builder.amount);
                p.sendMessage("§7Prix: §f" + builder.price + "€");
                p.sendMessage("§8────────────");

                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }

            // =========================
            // 🔙 RETOUR
            // =========================
            case 22 -> {
                p.closeInventory();
                ContractGUI.open(p);
            }
        }
    }
}