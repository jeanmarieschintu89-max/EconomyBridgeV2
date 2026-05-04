package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

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

                // 🔥 ACTIVE INPUT (indispensable pour l’Auth)

                AmountInputManager.wait(p, AmountInputManager.Type.WITHDRAW);
                InputManager.wait(p, "amount_input");

                p.sendMessage("§eEntre le montant à retirer dans le chat.");
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }

            case 22 -> new BankGUI().open(p);
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

        new WithdrawGUI().open(p);
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

        new WithdrawGUI().open(p);
    }
}