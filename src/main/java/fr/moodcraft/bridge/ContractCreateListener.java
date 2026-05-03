package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractCreateListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Créer contrat")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        int slot = e.getRawSlot();

        switch (slot) {

            // 📦 ITEM
            case 10 -> {
                e.setCancelled(false);
                return;
            }

            // 📊 QUANTITÉ → menu
            case 12 -> {
                p.closeInventory();
                ContractAmountGUI.open(p);
                return;
            }

            // 💰 PRIX → menu
            case 14 -> {
                p.closeInventory();
                ContractPriceGUI.open(p);
                return;
            }

            // ✅ VALIDER
            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                ContractManager.create(
                        p.getUniqueId(),
                        b.item,
                        b.amount,
                        b.price
                );

                p.sendMessage("§a✔ Contrat créé !");
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
                return;
            }

            // ❌ ANNULER
            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
                return;
            }
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);
        ContractCreateGUI.open(p);
    }
}