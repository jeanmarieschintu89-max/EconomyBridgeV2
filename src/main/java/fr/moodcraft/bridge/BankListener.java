package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().contains("Banque")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        String id = p.getUniqueId().toString();

        switch (e.getSlot()) {

            case 1 -> {
                p.closeInventory();
                p.sendMessage("§7IBAN: §b" + BankStorage.getIban(id));
            }

            case 2 -> {
                if (BankStorage.get(id) >= 1000) {
                    BankStorage.set(id, BankStorage.get(id) - 1000);
                    eco.depositPlayer(p, 1000);
                    p.sendMessage("§aRetrait +1000€");
                } else p.sendMessage("§cFonds insuffisants");

                BankGUI.open(p);
            }

            case 6 -> {
                if (eco.getBalance(p) >= 1000) {
                    eco.withdrawPlayer(p, 1000);
                    BankStorage.set(id, BankStorage.get(id) + 1000);
                    p.sendMessage("§bDepot 1000€");
                } else p.sendMessage("§cPas assez d'argent");

                BankGUI.open(p);
            }

            case 7 -> {
                p.closeInventory();
                BankHistoryGUI.open(p, 0);
            }

            case 8 -> BankGUI.open(p);
        }
    }
}