package fr.moodcraft.bridge;

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

        // 🔥 FIX TITRE (safe couleur)
        if (title == null || !title.contains("Contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        // 🔥 FIX SLOT (IMPORTANT)
        int slot = e.getSlot();

        var b = ContractBuilder.get(p);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // =========================
            // 👤 JOUEUR
            // =========================
            case 10 -> {
                p.closeInventory();
                TargetPlayerGUI.open(p);
            }

            // =========================
            // 📦 OBJET
            // =========================
            case 11 -> {

                ItemStack item = p.getInventory().getItemInMainHand();

                if (item == null || item.getType().isAir()) {
                    p.sendMessage("§cTu dois tenir un objet");
                    return;
                }

                b.itemStack = item.clone();
                b.item = item.getType().name().toLowerCase();
                b.amount = item.getAmount();

                p.sendMessage("§aObjet: §e" + b.item);

                ContractCreateGUI.open(p);
            }

            // =========================
            // 📄 QUANTITÉ
            // =========================
            case 12 -> {
                b.amount++;
                p.sendMessage("§eQuantité: §f" + b.amount);
                ContractCreateGUI.open(p);
            }

            // =========================
            // ➖ PRIX
            // =========================
            case 20 -> {
                b.price = Math.max(0, b.price - 100);
                p.sendMessage("§cPrix: §f" + b.price + "€");
                ContractCreateGUI.open(p);
            }

            // =========================
            // ➕ PRIX
            // =========================
            case 24 -> {
                b.price += 100;
                p.sendMessage("§aPrix: §f" + b.price + "€");
                ContractCreateGUI.open(p);
            }

            // =========================
            // ✔ VALIDER
            // =========================
            case 26 -> {

                if (b.target == null || b.item == null) {
                    p.sendMessage("§cDonnées incomplètes");
                    return;
                }

                ContractManager.create(
                        p.getName(),
                        b.target,
                        b.item,
                        b.amount,
                        b.price
                );

                p.sendMessage("§a✔ Contrat créé");
                p.closeInventory();
                ContractBuilder.remove(p);
            }

            // =========================
            // ❌ ANNULER
            // =========================
            case 18 -> {
                p.closeInventory();
                ContractBuilder.remove(p);
                p.sendMessage("§cContrat annulé");
            }
        }
    }
}