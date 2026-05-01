package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PayListener implements Listener {

    @EventHandler
    public void onPay(PlayerCommandPreprocessEvent e) {

        String msg = e.getMessage().toLowerCase();

        if (!msg.startsWith("/pay ")) return;

        String[] args = msg.split(" ");

        if (args.length < 3) return;

        String target = args[1];
        double amount;

        try {
            amount = Double.parseDouble(args[2]);
        } catch (Exception ex) {
            return;
        }

        String sender = e.getPlayer().getName();

        // 🔥 logs
        TransactionLogger.log(sender, "Paiement envoyé", amount);
        TransactionLogger.log(target, "Paiement reçu", amount);
    }
}