package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayListener implements Listener {

    private static final Map<UUID, Double> lastBalance = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrePay(PlayerCommandPreprocessEvent e) {

        String raw = e.getMessage();
        if (raw == null) return;

        String msg = raw.trim().replaceAll("\\s+", " ").toLowerCase();

        if (!msg.startsWith("/pay ")) return;

        Player p = e.getPlayer();

        // 📌 stocke balance AVANT
        lastBalance.put(p.getUniqueId(),
                VaultHook.getEconomy().getBalance(p));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPay(PlayerCommandPreprocessEvent e) {

        String raw = e.getMessage();
        if (raw == null) return;

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
            return;
        }

        if (amount <= 0) return;

        var eco = VaultHook.getEconomy();
        if (eco == null) return;

        Double before = lastBalance.remove(senderPlayer.getUniqueId());
        if (before == null) return;

        double after = eco.getBalance(senderPlayer);

        double real = before - after;

        // 🔒 sécurité → évite faux logs
        if (real <= 0) return;

        var target = Bukkit.getPlayerExact(targetName);
        String targetFinal = target != null ? target.getName() : targetName;

        // 🧾 logs RÉELS
        EconomyListener.log(sender, "Paiement vers " + targetFinal, real);
        EconomyListener.log(targetFinal, "Paiement reçu de " + sender, real);
    }
}