package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

public class BankListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();

        // 🔥 FIX titre (Bedrock safe)
        if (title == null || !title.contains("Banque")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        Economy eco = VaultHook.getEconomy();
        if (eco == null) {
            p.sendMessage("§cErreur economie");
            return;
        }

        String id = p.getUniqueId().toString();
        int slot = e.getSlot();

        // 🔊 feedback
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        switch (slot) {

            // =========================
            // 📤 IBAN
            // =========================
            case 1 -> {
                String iban = BankStorage.getIban(id);

                p.closeInventory();

                p.sendMessage("§8────────────");
                p.sendMessage("§eBanque MoodCraft");
                p.sendMessage("§7Titulaire: §e" + p.getName());
                p.sendMessage("§7IBAN: §b" + iban);
                p.sendMessage("§8────────────");
            }

            // =========================
            // 💸 RETRAIT
            // =========================
            case 2 -> {

                double bank = BankStorage.get(id);

                if (bank >= 1000) {

                    BankStorage.set(id, bank - 1000);
                    eco.depositPlayer(p, 1000);

                    TransactionLogger.log(p.getName(), "Retrait", 1000);

                    p.sendMessage("§a+1000€ retire");

                } else {
                    p.sendMessage("§cPas assez en banque");
                }

                BankGUI.open(p);
            }

            // =========================
            // 💰 DEPOT
            // =========================
            case 6 -> {

                if (eco.getBalance(p) >= 1000) {

                    eco.withdrawPlayer(p, 1000);

                    double bank = BankStorage.get(id);
                    BankStorage.set(id, bank + 1000);

                    TransactionLogger.log(p.getName(), "Depot", 1000);

                    p.sendMessage("§b1000€ depose");

                } else {
                    p.sendMessage("§cPas assez d'argent");
                }

                BankGUI.open(p);
            }

            // =========================
            // 📄 HISTORIQUE
            // =========================
            case 7 -> {
                p.closeInventory();
                BankHistoryGUI.open(p, 0);
            }

            // =========================
            // 🔄 REFRESH
            // =========================
            case 8 -> BankGUI.open(p);
        }
    }
}