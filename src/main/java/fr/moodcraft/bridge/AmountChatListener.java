package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AmountChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!AmountInputManager.isWaiting(p)) return;

        e.setCancelled(true);

        String msg = e.getMessage().replace(",", ".").trim();

        double amount;
        try {
            amount = Double.parseDouble(msg);
        } catch (Exception ex) {
            p.sendMessage("§cMontant invalide.");
            return;
        }

        if (amount <= 0) {
            p.sendMessage("§cMontant invalide.");
            return;
        }

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        AmountInputManager.Type type = AmountInputManager.get(p);
        AmountInputManager.remove(p);

        if (type == AmountInputManager.Type.DEPOSIT) {

            double cash = eco.getBalance(p);

            if (cash < amount) {
                p.sendMessage("§cPas assez d'argent liquide.");
                return;
            }

            eco.withdrawPlayer(p, amount);
            BankStorage.add(p.getUniqueId().toString(), amount);

            p.sendMessage("§aDépôt de §f" + SafeGUI.money(amount));

            Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                    new DepositGUI().open(p)); // ✅ FIX
        } else {

            double bank = BankStorage.get(p.getUniqueId().toString());

            if (bank < amount) {
                p.sendMessage("§cPas assez en banque.");
                return;
            }

            BankStorage.remove(p.getUniqueId().toString(), amount);
            eco.depositPlayer(p, amount);

            p.sendMessage("§aRetrait de §f" + SafeGUI.money(amount));

            Bukkit.getScheduler().runTask(Main.getInstance(), () ->
                    new WithdrawGUI().open(p)); // ✅ FIX
        }

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
}