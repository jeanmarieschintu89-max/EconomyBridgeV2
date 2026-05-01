package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

public class BankListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6Banque")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        String id = p.getUniqueId().toString();

        switch (e.getSlot()) {

            // ➖ RETIRER
            case 2 -> {

                double bank = BankStorage.get(id);

                if (bank >= 1000) {

                    BankStorage.set(id, bank - 1000);
                    eco.depositPlayer(p, 1000);

                    // 🔥 LOG
                    TransactionLogger.log(p.getName(), "WITHDRAW", 1000);

                    p.sendMessage("§a✔ +1000€ retiré de la banque");

                } else {
                    p.sendMessage("§c❌ Pas assez d'argent en banque");
                }

                BankGUI.open(p);
            }

            // ➕ DEPOSER
            case 6 -> {

                if (eco.getBalance(p) >= 1000) {

                    eco.withdrawPlayer(p, 1000);

                    double bank = BankStorage.get(id);
                    BankStorage.set(id, bank + 1000);

                    // 🔥 LOG
                    TransactionLogger.log(p.getName(), "DEPOSIT", 1000);

                    p.sendMessage("§b✔ 1000€ déposé en banque");

                } else {
                    p.sendMessage("§c❌ Pas assez d'argent");
                }

                BankGUI.open(p);
            }

            // 🔄 REFRESH
            case 8 -> BankGUI.open(p);
        }
    }
}