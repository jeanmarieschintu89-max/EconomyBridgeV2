package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AmountChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if (!AmountInputManager.has(p)) return;

        e.setCancelled(true);

        String msg = e.getMessage();
        AmountInputManager.Type type = AmountInputManager.getType(p);

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            try {
                double amount = Double.parseDouble(msg.replace(",", "."));

                if (amount <= 0) {
                    p.sendMessage("§cMontant invalide.");
                    return;
                }

                switch (type) {

                    case DEPOSIT -> {
                        double cash = VaultHook.getBalance(p);

                        if (cash < amount) {
                            p.sendMessage("§cPas assez d'argent.");
                            return;
                        }

                        VaultHook.remove(p, amount);
                        BankStorage.add(p.getUniqueId().toString(), amount);

                        p.sendMessage("§a✔ Déposé: §e" + (int) amount + "€");
                    }

                    case WITHDRAW -> {

                        double bank = BankStorage.get(p.getUniqueId().toString());

                        if (bank < amount) {
                            p.sendMessage("§cFonds insuffisants.");
                            return;
                        }

                        BankStorage.remove(p.getUniqueId().toString(), amount);
                        VaultHook.add(p, amount);

                        p.sendMessage("§a✔ Retiré: §e" + (int) amount + "€");
                    }
                }

                AmountInputManager.clear(p);
                BankGUI.open(p);

            } catch (Exception ex) {
                p.sendMessage("§cNombre invalide.");
            }
        });
    }
}