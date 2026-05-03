package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.milkbowl.vault.economy.Economy;

public class BankListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION ULTRA SAFE
        String clean = title.replaceAll("§.", "")
                .toLowerCase()
                .trim();

        // 🔐 MATCH LARGE (évite 100% des bugs de titre)
        if (!clean.contains("banque")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;

        // 🔒 bloque UNIQUEMENT le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        // 🔥 ON BLOQUE TOUJOURS
        e.setCancelled(true);

        // 🔥 anti glitch
        if (e.isShiftClick()) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();

        Economy eco = VaultHook.getEconomy();
        if (eco == null) {
            p.sendMessage("§c❌ Erreur économie");
            return;
        }

        String id = p.getUniqueId().toString();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            // 📄 IBAN
            case 0 -> showIban(p, id);

            // 💸 RETRAIT
            case 1 -> withdraw(p, eco, id);

            // 🔁 VIREMENT
            case 2 -> {
                p.closeInventory();

                TransferBuilder.remove(p);
                TransferBuilder.create(p);

                BankTransferGUI.open(p);
            }

            // 💰 DEPOT
            case 6 -> deposit(p, eco, id);

            // 📜 HISTORIQUE
            case 7 -> {
                p.closeInventory();
                BankHistoryGUI.open(p, 0);
            }

            // 🔙 RETOUR
            case 8 -> {
                p.closeInventory();
                MainMenuGUI.open(p);
            }
        }
    }

    private void showIban(Player p, String id) {

        String iban = BankStorage.getIban(id);

        p.closeInventory();

        p.sendMessage("§8════════════");
        p.sendMessage("§6🏦 Banque MoodCraft");
        p.sendMessage("");
        p.sendMessage("§7Titulaire: §f" + p.getName());
        p.sendMessage("§7IBAN: §b" + iban);
        p.sendMessage("");
        p.sendMessage("§8════════════");
    }

    private void withdraw(Player p, Economy eco, String id) {

        double bank = BankStorage.get(id);

        if (bank >= 1000) {

            BankStorage.set(id, bank - 1000);
            eco.depositPlayer(p, 1000);

            TransactionLogger.log(p.getName(), "Retrait", 1000);

            p.sendMessage("§8════════════");
            p.sendMessage("§a✔ Retrait effectué");
            p.sendMessage("§7+1000€ en liquide");
            p.sendMessage("§8════════════");

        } else {
            p.sendMessage("§c❌ Solde insuffisant en banque");
        }

        BankGUI.open(p);
    }

    private void deposit(Player p, Economy eco, String id) {

        if (eco.getBalance(p) >= 1000) {

            eco.withdrawPlayer(p, 1000);

            double bank = BankStorage.get(id);
            BankStorage.set(id, bank + 1000);

            TransactionLogger.log(p.getName(), "Depot", 1000);

            p.sendMessage("§8════════════");
            p.sendMessage("§b✔ Dépôt effectué");
            p.sendMessage("§7+1000€ en banque");
            p.sendMessage("§8════════════");

        } else {
            p.sendMessage("§c❌ Pas assez d'argent");
        }

        BankGUI.open(p);
    }
}