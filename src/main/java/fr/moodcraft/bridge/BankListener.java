package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

public class BankListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6🏦 Banque")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        String id = p.getUniqueId().toString();

        switch (e.getSlot()) {

            case 1 -> {
                String iban = BankStorage.getIban(id);

                p.closeInventory();

                p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
                p.sendMessage("§e🏦 Banque MoodCraft");
                p.sendMessage("§7Titulaire: §e" + p.getName());
                p.sendMessage("§7IBAN: §b" + iban);
                p.sendMessage("§8━━━━━━━━━━━━━━━━━━");
            }

            case 2 -> {
                double bank = BankStorage.get(id);

                if (bank >= 1000) {
                    BankStorage.set(id, bank - 1000);
                    eco.depositPlayer(p, 1000);

                    TransactionLogger.log(p.getName(), "Retrait", 1000);

                    p.sendMessage("§a✔ +1000€ retiré de la banque");
                } else {
                    p.sendMessage("§c❌ Pas assez d'argent en banque");
                }

                BankGUI.open(p);
            }

            case 6 -> {
                if (eco.getBalance(p) >= 1000) {

                    eco.withdrawPlayer(p, 1000);

                    double bank = BankStorage.get(id);
                    BankStorage.set(id, bank + 1000);

                    TransactionLogger.log(p.getName(), "Dépôt", 1000);

                    p.sendMessage("§b✔ 1000€ déposé en banque");

                } else {
                    p.sendMessage("§c❌ Pas assez d'argent");
                }

                BankGUI.open(p);
            }

            case 7 -> {
                p.closeInventory();
                BankHistoryGUI.open(p, 0, "ALL");
            }

            case 8 -> BankGUI.open(p);
        }
    }
}