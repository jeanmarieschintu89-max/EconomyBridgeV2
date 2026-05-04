package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class DepositHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 💰 montants rapides
            case 11 -> deposit(p, 100);
            case 13 -> deposit(p, 1000);
            case 15 -> deposit(p, 10000);

            // 🔥 MAX
            case 20 -> depositAll(p);

            // 💬 PERSONNALISÉ
            case 24 -> {
                p.closeInventory();

                // 🔥 ACTIVE INPUT (clé du système)

                AmountInputManager.wait(p, AmountInputManager.Type.DEPOSIT);
                InputManager.wait(p, "amount_input");

                p.sendMessage("§eEntre le montant à déposer dans le chat.");
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }

            // 🔙 RETOUR
            case 22 -> BankGUI.open(p);
        }
    }

    // =========================
    // 💰 DÉPÔT NORMAL
    // =========================
    private void deposit(Player p, double amount) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) {
            p.sendMessage("§cErreur économie (Vault)");
            return;
        }

        double cash = eco.getBalance(p);

        if (cash < amount) {
            p.sendMessage("§cPas assez d'argent liquide.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        eco.withdrawPlayer(p, amount);
        BankStorage.add(p.getUniqueId().toString(), amount);

        p.sendMessage("§a✔ Déposé: §f" + SafeGUI.money(amount));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        DepositGUI.open(p);
    }

    // =========================
    // 🔥 DÉPÔT MAX
    // =========================
    private void depositAll(Player p) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) {
            p.sendMessage("§cErreur économie (Vault)");
            return;
        }

        double cash = eco.getBalance(p);

        if (cash <= 0) {
            p.sendMessage("§cTu n'as rien à déposer.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        eco.withdrawPlayer(p, cash);
        BankStorage.add(p.getUniqueId().toString(), cash);

        p.sendMessage("§a✔ Déposé (tout): §f" + SafeGUI.money(cash));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        DepositGUI.open(p);
    }
}