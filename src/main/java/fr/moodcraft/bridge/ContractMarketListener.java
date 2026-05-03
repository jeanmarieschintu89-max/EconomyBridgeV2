package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractMarketListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Marché des contrats")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        // 🔒 protège inventaire joueur
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        // 🔙 RETOUR
        if (slot == 49) {
            p.closeInventory();
            ContractGUI.open(p);
            return;
        }

        // 📜 CLICK CONTRAT
        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();

        if (!name.contains("#")) return;

        try {
            int id = Integer.parseInt(name.split("#")[1]);

            Contract c = ContractManager.get(id);

            if (c == null) {
                p.sendMessage("§c❌ Contrat introuvable");
                return;
            }

            // 🔒 déjà pris
            if (ContractManager.isAlreadyTaken(id)) {
                p.sendMessage("§c❌ Déjà pris");
                return;
            }

            // 🔒 déjà un contrat actif
            if (ContractManager.hasActiveContract(p.getUniqueId())) {
                p.sendMessage("§c❌ Tu as déjà un contrat actif");
                return;
            }

            // ✅ ACCEPTATION
            c.worker = p.getUniqueId();
            c.status = Contract.Status.ACCEPTED;

            p.sendMessage("§8────────────");
            p.sendMessage("§a✔ Contrat accepté !");
            p.sendMessage("§7Objet: §f" + c.item);
            p.sendMessage("§7Quantité: §a" + c.amount);
            p.sendMessage("§7Gain: §6" + (c.amount * c.price) + "€");
            p.sendMessage("§8────────────");

            p.closeInventory();

        } catch (Exception ex) {
            p.sendMessage("§cErreur lecture contrat");
        }
    }
}