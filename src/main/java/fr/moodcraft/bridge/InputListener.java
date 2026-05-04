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

        e.setCancelled(true); // 🔒 bloque le chat public

        String type = InputManager.get(p);
        String msg = e.getMessage();

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            try {
                double amount = Double.parseDouble(msg.replace(",", "."));

                if (amount <= 0) {
                    p.sendMessage("§cMontant invalide");
                    return;
                }

                switch (type) {

                    case "deposit" -> {
                        double cash = VaultHook.getBalance(p);
                        if (cash < amount) {
                            p.sendMessage("§cPas assez d'argent liquide");
                            return;
                        }
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

                    default -> {
                        p.sendMessage("§cType d'input inconnu");
                    }
                }

                InputManager.clear(p); // 🔥 on clear ici
                BankGUI.open(p);

            } catch (Exception ex) {
                p.sendMessage("§cNombre invalide (ex: 1000)");
            }
        });
    }
}