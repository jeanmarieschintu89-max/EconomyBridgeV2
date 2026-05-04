package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputListener implements Listener {

    // 🔥 stockage temporaire IBAN
    private static final Map<UUID, String> ibanCache = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        // =========================
        // 💳 IBAN INPUT
        // =========================
        if (InputManager.has(p)) {

            e.setCancelled(true);

            String type = InputManager.get(p);
            String msg = e.getMessage();

            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

                switch (type) {

                    case "iban_input" -> {

                        if (msg.length() < 6) {
                            p.sendMessage("§cIBAN invalide.");
                            return;
                        }

                        ibanCache.put(p.getUniqueId(), msg);

                        p.sendMessage("§a✔ IBAN enregistré: §e" + msg);

                        // 👉 on enchaîne sur le montant
                        AmountInputManager.wait(p, AmountInputManager.Type.WITHDRAW);

                        p.sendMessage("§eEntre maintenant le montant à envoyer.");
                    }

                    default -> {
                        p.sendMessage("§cInput inconnu.");
                    }
                }

                InputManager.clear(p);
            });

            return;
        }

        // =========================
        // 💰 AMOUNT INPUT (IBAN)
        // =========================
        if (AmountInputManager.has(p)) {

            e.setCancelled(true);

            String msg = e.getMessage();

            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

                try {
                    double amount = Double.parseDouble(msg.replace(",", "."));

                    if (amount <= 0) {
                        p.sendMessage("§cMontant invalide.");
                        return;
                    }

                    String iban = ibanCache.get(p.getUniqueId());

                    if (iban == null) {
                        p.sendMessage("§cErreur IBAN.");
                        return;
                    }

                    double bank = BankStorage.get(p.getUniqueId().toString());

                    if (bank < amount) {
                        p.sendMessage("§cFonds insuffisants.");
                        return;
                    }

                    // 💸 VIREMENT
                    BankStorage.remove(p.getUniqueId().toString(), amount);

                    p.sendMessage("§a✔ Virement envoyé !");
                    p.sendMessage("§7Montant: §e" + (int) amount + "€");
                    p.sendMessage("§7Vers IBAN: §e" + iban);

                    // 🔥 nettoyage complet
                    ibanCache.remove(p.getUniqueId());
                    AmountInputManager.clear(p);
                    p.removeMetadata("input_active", Main.getInstance());

                    BankGUI.open(p);

                } catch (Exception ex) {
                    p.sendMessage("§cNombre invalide.");
                }
            });
        }
    }
}