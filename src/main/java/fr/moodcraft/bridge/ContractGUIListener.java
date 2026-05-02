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

        if (title == null || !title.contains("Contrat")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        int slot = e.getRawSlot();
        if (slot > 26) return;

        var b = ContractBuilder.get(p);

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // =========================
            // 👤 JOUEUR (cible)
            // =========================
            case 10 -> {
                p.closeInventory();
                TargetPlayerGUI.open(p);
            }

            // =========================
            // 📦 OBJET (main du joueur)
            // =========================
            case 11 -> {

                ItemStack item = p.getInventory().getItemInMainHand();

                if (item == null || item.getType().isAir()) {
                    p.sendMessage("§cTu dois tenir un objet");
                    return;
                }

                b.item = item.getType().name().toLowerCase();
                b.amount = item.getAmount(); // 🔥 auto quantité

                p.sendMessage("§aObjet sélectionné: §e" + b.item + " x" + b.amount);

                ContractCreateGUI.open(p);
            }

            // =========================
            // 📄 QUANTITÉ (option simple)
            // =========================
            case 12 -> {

                if (b.amount <= 0) b.amount = 1;
                else b.amount += 1;

                p.sendMessage("§eQuantité: §f" + b.amount);

                ContractCreateGUI.open(p);
            }

            // =========================
            // ➖ PRIX
            // =========================
            case 20 -> {

                b.price -= 100;
                if (b.price < 0) b.price = 0;

                ContractCreateGUI.open(p);
            }

            // =========================
            // ➕ PRIX
            // =========================
            case 24 -> {

                b.price += 100;

                ContractCreateGUI.open(p);
            }

            // =========================
            // ✔ VALIDER
            // =========================
            case 26 -> {

                if (b.target == null) {
                    p.sendMessage("§cChoisis un joueur");
                    return;
                }

                if (b.item == null) {
                    p.sendMessage("§cChoisis un objet");
                    return;
                }

                if (b.amount <= 0) {
                    p.sendMessage("§cQuantité invalide");
                    return;
                }

                if (b.price <= 0) {
                    p.sendMessage("§cPrix invalide");
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
            }

            // =========================
            // ❌ ANNULER
            // =========================
            case 18 -> {
                p.closeInventory();
                p.sendMessage("§cCréation annulée");
            }
        }
    }
}