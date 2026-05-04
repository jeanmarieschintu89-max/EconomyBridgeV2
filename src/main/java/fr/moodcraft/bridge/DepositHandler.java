package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DepositHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> deposit(p, 100);
            case 13 -> deposit(p, 1000);
            case 15 -> deposit(p, 10000);

            case 22 -> BankGUI.open(p);
        }
    }

    private void deposit(Player p, int amount) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double cash = eco.getBalance(p);

        if (cash < amount) {
            p.sendMessage("§cPas assez d'argent liquide.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        // 💰 TRANSFERT DIRECT
        eco.withdrawPlayer(p, amount);
        BankStorage.add(p.getUniqueId().toString(), amount);

        p.sendMessage("§aDépôt de §f" + SafeGUI.money(amount) + " §aeffectué !");
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        // 🔄 refresh GUI
        DepositGUI.open(p);
    }
}