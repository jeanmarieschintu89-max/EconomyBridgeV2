package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InputListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!InputManager.has(p)) return;

        e.setCancelled(true);

        String type = InputManager.get(p);
        String msg = e.getMessage();

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            switch (type) {

                case "iban_input" -> {

                    // 👉 ici tu traites l’IBAN
                    p.sendMessage("§aIBAN reçu: §e" + msg);

                    // 🔥 à remplacer par ton vrai système
                    // IbanManager.startTransfer(p, msg);

                    p.sendMessage("§7➜ Entre maintenant le montant avec /ibanpay");

                }

                default -> {
                    p.sendMessage("§cInput inconnu.");
                }
            }

            InputManager.clear(p);
        });
    }
}