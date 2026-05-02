package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.milkbowl.vault.economy.Economy;

public class TransferListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION TITRE (anti bug couleurs / Bedrock)
        String clean = title.replaceAll("§.", "");

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getSlot();

        var b = TransferBuilder.get(p);

        // =========================
        // 💸 MENU PRINCIPAL
        // =========================
        if (clean.equalsIgnoreCase("Virement")) {

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

            switch (slot) {

                case 11 -> {
                    p.closeInventory();

                    p.sendMessage("§8────────────");
                    p.sendMessage("§eVirement IBAN");
                    p.sendMessage("§7Commande:");
                    p.sendMessage("§f/ibanpay <iban> <montant>");
                    p.sendMessage("§8────────────");
                }

                case 13 -> {
                    p.closeInventory();
                    TransferTargetGUI.open(p);
                }

                case 15 -> {
                    p.closeInventory();
                    BankGUI.open(p);
                }
            }

            return;
        }

        // =========================
        // 👤 CHOIX JOUEUR
        // =========================
        if (clean.equalsIgnoreCase("Choisir joueur virement")) {

            var item = e.getCurrentItem();
            if (item == null || !item.hasItemMeta()) return;

            String name = item.getItemMeta().getDisplayName().replace("§a", "");

            b.target = name;

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

            p.sendMessage("§aCible: §f" + name);

            TransferConfirmGUI.open(p);
            return;
        }

        // =========================
        // 💰 CONFIRMATION
        // =========================
        if (clean.equalsIgnoreCase("Confirmation virement")) {

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);

            switch (slot) {

                case 11 -> {
                    b.amount = Math.max(0, b.amount - 100);
                    p.sendMessage("§c-" + 100 + "€");
                }

                case 15 -> {
                    b.amount += 100;
                    p.sendMessage("§a+" + 100 + "€");
                }

                case 26 -> {

                    Player target = Bukkit.getPlayerExact(b.target);
                    Economy eco = VaultHook.getEconomy();

                    if (target == null) {
                        p.sendMessage("§cJoueur offline");
                        return;
                    }

                    if (eco.getBalance(p) < b.amount) {
                        p.sendMessage("§cPas assez d'argent");
                        return;
                    }

                    eco.withdrawPlayer(p, b.amount);
                    eco.depositPlayer(target, b.amount);

                    p.sendMessage("§a✔ Virement envoyé");
                    target.sendMessage("§a+" + b.amount + "€ reçu de " + p.getName());

                    TransactionLogger.log(p.getName(), "Virement vers " + target.getName(), b.amount);

                    TransferBuilder.remove(p);
                    p.closeInventory();
                    return;
                }

                case 18 -> {
                    TransferBuilder.remove(p);
                    p.closeInventory();
                    return;
                }
            }

            // 🔄 refresh GUI (IMPORTANT)
            TransferConfirmGUI.open(p);
        }
    }
}