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

        if (title == null || !title.contains("Banque")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        var item = e.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        int slot = e.getRawSlot();
        if (slot > 8) return;

        Economy eco = VaultHook.getEconomy();
        if (eco == null) {
            p.sendMessage("§cErreur économie");
            return;
        }

        String id = p.getUniqueId().toString();

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

        switch (slot) {

            case 0 -> showIban(p, id);

            case 1 -> withdraw(p, eco, id);

            case 2 -> {
                p.closeInventory();
                BankTransferGUI.open(p);
            }

            case 6 -> deposit(p, eco, id);

            case 7 -> {
                p.closeInventory();
                BankHistoryGUI.open(p, 0);
            }

            case 8 -> BankGUI.open(p);
        }
    }

    private void showIban(Player p, String id) {

        String iban = BankStorage.getIban(id);

        p.closeInventory();

        p.sendMessage("§8────────────");
        p.sendMessage("§eBanque MoodCraft");
        p.sendMessage("§7Titulaire: §e" + p.getName());
        p.sendMessage("§7IBAN: §b" + iban);
        p.sendMessage("§8────────────");
    }

    private void withdraw(Player p, Economy eco, String id) {

        double bank = BankStorage.get(id);

        if (bank >= 1000) {

            BankStorage.set(id, bank - 1000);
            eco.depositPlayer(p, 1000);

            TransactionLogger.log(p.getName(), "Retrait", 1000);

            p.sendMessage("§8────────────");
            p.sendMessage("§aRetrait effectué");
            p.sendMessage("§7Montant: §a+1000€");
            p.sendMessage("§8────────────");

        } else {
            p.sendMessage("§cSolde insuffisant en banque");
        }

        BankGUI.open(p);
    }

    private void deposit(Player p, Economy eco, String id) {

        if (eco.getBalance(p) >= 1000) {

            eco.withdrawPlayer(p, 1000);

            double bank = BankStorage.get(id);
            BankStorage.set(id, bank + 1000);

            TransactionLogger.log(p.getName(), "Depot", 1000);

            p.sendMessage("§8────────────");
            p.sendMessage("§bDépôt effectué");
            p.sendMessage("§7Montant: §b+1000€");
            p.sendMessage("§8────────────");

        } else {
            p.sendMessage("§cPas assez d'argent");
        }

        BankGUI.open(p);
    }
}