package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PayListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPay(PlayerCommandPreprocessEvent e) {

        String raw = e.getMessage();
        if (raw == null) return;

        // 👉 normalise (espaces multiples, casse)
        String msg = raw.trim().replaceAll("\\s+", " ").toLowerCase();

        if (!msg.startsWith("/pay ")) return;

        String[] args = msg.split(" ");
        if (args.length < 3) return;

        Player senderPlayer = e.getPlayer();
        String sender = senderPlayer.getName();

        String targetName = args[1];

        double amount;
        try {
            amount = Double.parseDouble(args[2].replace(",", "."));
        } catch (Exception ex) {
            return; // montant invalide
        }

        if (amount <= 0) return;

        // 🔎 vérifie que la cible existe (au moins offline)
        var offline = Bukkit.getOfflinePlayer(targetName);
        if (offline == null || offline.getName() == null) return;

        // ⚠️ On est en MONITOR + ignoreCancelled = true
        // → la commande /pay a déjà été exécutée avec succès par le plugin éco

        // 🧾 logs propres
        TransactionLogger.log(sender, "Paiement envoye", amount);
        TransactionLogger.log(offline.getName(), "Paiement recu", amount);
    }
}