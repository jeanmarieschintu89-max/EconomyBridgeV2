package fr.moodcraft.bridge;

import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class InputListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!InputManager.has(p)) return;

        e.setCancelled(true); // 🔥 bloque chat public

        String type = InputManager.get(p);
        String msg = e.getMessage();

        InputManager.clear(p);

        // 🔥 revenir sur thread principal
        org.bukkit.Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            try {
                double amount = Double.parseDouble(msg);

                if (amount <= 0) {
                    p.sendMessage("§cMontant invalide");
                    return;
                }

                switch (type) {

                    case "deposit" -> {
                        VaultHook.remove(p, amount);
                        BankStorage.add(p.getUniqueId().toString(), amount);
                        p.sendMessage("§a✔ Déposé: §e" + (int) amount + "€");
                    }

                    case "withdraw" -> {
                        double bank = BankStorage.get(p.getUniqueId().toString());

                        if (bank < amount) {
                            p.sendMessage("§cFonds insuffisants");
                            return;
                        }

                        BankStorage.remove(p.getUniqueId().toString(), amount);
                        VaultHook.add(p, amount);
                        p.sendMessage("§a✔ Retiré: §e" + (int) amount + "€");
                    }
                }

                BankGUI.open(p);

            } catch (Exception ex) {
                p.sendMessage("§cNombre invalide");
            }
        });
    }
}