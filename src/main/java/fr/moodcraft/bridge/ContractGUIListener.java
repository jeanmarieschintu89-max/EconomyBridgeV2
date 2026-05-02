package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ContractGUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");

        if (!clean.equalsIgnoreCase("Contrats")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

        switch (slot) {

            // =========================
            // 📝 CRÉER CONTRAT
            // =========================
            case 11 -> {
                p.closeInventory();

                p.sendMessage("§8────────────");
                p.sendMessage("§a📄 Création de contrat");
                p.sendMessage("");
                p.sendMessage("§7Commande:");
                p.sendMessage("§e/contract <item> <quantité> <prix>");
                p.sendMessage("");
                p.sendMessage("§7Exemple:");
                p.sendMessage("§f/contract diamond 32 5000");
                p.sendMessage("§8────────────");
            }

            // =========================
            // 📜 CONTRATS DISPONIBLES
            // =========================
            case 13 -> {
                p.closeInventory();
                ContractMarketGUI.open(p);
            }

            // =========================
            // 📦 MES CONTRATS
            // =========================
            case 15 -> {
                p.closeInventory();
                p.sendMessage("§7🚧 En développement...");
                // 👉 futur: ContractPlayerGUI.open(p);
            }

            // =========================
            // 🔙 RETOUR
            // =========================
            case 22 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }
}