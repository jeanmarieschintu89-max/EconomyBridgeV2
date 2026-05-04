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

            // 🔥 MAX
            case 20 -> depositAll(p);

            // 💬 CUSTOM
            case 24 -> {
                p.closeInventory();
                AmountInputManager.wait(p, AmountInputManager.Type.DEPOSIT);
                p.sendMessage("§eEntre le montant à déposer dans le chat.");
            }

            case 22 -> BankGUI.open(p);
        }
    }

    // =========================
    // 💰 DÉPÔT NORMAL
    // =========================
    private void deposit(Player p, double amount) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double cash = eco.getBalance(p);

        if (cash < amount) {
            p.sendMessage("§cPas assez d'argent liquide.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        eco.withdrawPlayer(p, amount);
        BankStorage.add(p.getUniqueId().toString(), amount);

        p.sendMessage("§aDépôt de §f" + SafeGUI.money(amount) + " §aeffectué !");
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        DepositGUI.open(p);
    }

    // =========================
    // 🔥 DÉPÔT MAX
    // =========================
    private void depositAll(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double cash = eco.getBalance(p);

        if (cash <= 0) {
            p.sendMessage("§cTu n'as rien à déposer.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        eco.withdrawPlayer(p, cash);
        BankStorage.add(p.getUniqueId().toString(), cash);

        p.sendMessage("§aTu as tout déposé : §f" + SafeGUI.money(cash));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        DepositGUI.open(p);
    }
}