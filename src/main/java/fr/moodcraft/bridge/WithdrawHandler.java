package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class WithdrawHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 11 -> withdraw(p, 100);
            case 13 -> withdraw(p, 1000);
            case 15 -> withdraw(p, 10000);

            // 🔥 MAX
            case 20 -> withdrawAll(p);

            // 💬 CUSTOM
            case 24 -> {
                p.closeInventory();
                AmountInputManager.wait(p, AmountInputManager.Type.WITHDRAW);
                p.sendMessage("§eEntre le montant à retirer dans le chat.");
            }

            case 22 -> BankGUI.open(p);
        }
    }

    // =========================
    // 💸 RETRAIT NORMAL
    // =========================
    private void withdraw(Player p, double amount) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double bank = BankStorage.get(p.getUniqueId().toString());

        if (bank < amount) {
            p.sendMessage("§cPas assez d'argent en banque.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        BankStorage.remove(p.getUniqueId().toString(), amount);
        eco.depositPlayer(p, amount);

        p.sendMessage("§aRetrait de §f" + SafeGUI.money(amount) + " §aeffectué !");
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        WithdrawGUI.open(p);
    }

    // =========================
    // 🔥 RETRAIT MAX
    // =========================
    private void withdrawAll(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double bank = BankStorage.get(p.getUniqueId().toString());

        if (bank <= 0) {
            p.sendMessage("§cTu n'as rien en banque.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        BankStorage.remove(p.getUniqueId().toString(), bank);
        eco.depositPlayer(p, bank);

        p.sendMessage("§aTu as tout retiré : §f" + SafeGUI.money(bank));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        WithdrawGUI.open(p);
    }
}