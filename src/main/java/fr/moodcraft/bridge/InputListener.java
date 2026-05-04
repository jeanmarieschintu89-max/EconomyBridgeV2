package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputListener implements Listener {

    private static final Map<UUID, String> ibanCache = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
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

                if (type.equals("iban_input")) {

                    if (msg.length() < 6) {
                        p.sendMessage("§cIBAN invalide.");
                        return;
                    }

                    ibanCache.put(p.getUniqueId(), msg);

                    p.sendMessage("§a✔ IBAN enregistré: §e" + msg);

                    // 👉 maintenant montant
                    AmountInputManager.wait(p, AmountInputManager.Type.WITHDRAW);

                    p.sendMessage("§eEntre le montant à envoyer.");
                }

                InputManager.clear(p);
            });

            return;
        }

        // =========================
        // 💰 AMOUNT INPUT
        // =========================
        if (AmountInputManager.has(p)) {

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

                        // =========================
                        // 💰 DÉPÔT
                        // =========================
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

                        // =========================
                        // 💸 RETRAIT
                        // =========================
                        case WITHDRAW -> {

                            // 🔥 cas IBAN actif
                            if (ibanCache.containsKey(p.getUniqueId())) {

                                String iban = ibanCache.get(p.getUniqueId());
                                double bank = BankStorage.get(p.getUniqueId().toString());

                                if (bank < amount) {
                                    p.sendMessage("§cFonds insuffisants.");
                                    return;
                                }

                                BankStorage.remove(p.getUniqueId().toString(), amount);

                                p.sendMessage("§a✔ Virement envoyé !");
                                p.sendMessage("§7Montant: §e" + (int) amount + "€");
                                p.sendMessage("§7IBAN: §e" + iban);

                                ibanCache.remove(p.getUniqueId());

                            } else {

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
                    }

                    AmountInputManager.clear(p);
                    BankGUI.open(p);

                } catch (Exception ex) {
                    p.sendMessage("§cNombre invalide.");
                }
            });
        }
    }
}