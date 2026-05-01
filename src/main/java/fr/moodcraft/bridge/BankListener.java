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

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        switch (e.getSlot()) {

            case 2 -> {
                eco.depositPlayer(p, 1000);
                p.sendMessage("§a✔ +1000€ ajouté");
                BankGUI.open(p);
            }

            case 6 -> {
                if (eco.getBalance(p) >= 1000) {
                    eco.withdrawPlayer(p, 1000);
                    p.sendMessage("§c✔ -1000€ retiré");
                } else {
                    p.sendMessage("§c❌ Pas assez d'argent");
                }
                BankGUI.open(p);
            }

            case 8 -> BankGUI.open(p);
        }
    }
}