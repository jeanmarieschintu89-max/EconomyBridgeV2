package fr.moodcraft.bridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanqueAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // ❌ console
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande joueur uniquement.");
            return true;
        }

        // 🔐 permission
        if (!p.hasPermission("econ.admin")) {
            p.sendMessage("§c❌ Permission refusée.");
            return true;
        }

        // =========================
        // ⚙️ SOUS-COMMANDES
        // =========================
        if (args.length > 0) {

            switch (args[0].toLowerCase()) {

                // 🔄 RELOAD CONFIG
                case "reload" -> {
                    Main plugin = Main.getInstance();
                    plugin.reloadConfig();

                    p.sendMessage("§b✔ Config rechargée");
                    return true;
                }

                // 🧨 RESET ECONOMIE
                case "reset" -> {
                    for (String item : MarketState.base.keySet()) {
                        double base = MarketState.base.get(item);
                        MarketState.setPrice(item, base);
                        PriceUpdater.updateItem(item);
                    }

                    p.sendMessage("§4✔ Économie reset");
                    return true;
                }

                default -> {
                    p.sendMessage("§cUsage: /banqueadmin [reload|reset]");
                    return true;
                }
            }
        }

        // =========================
        // 🎮 GUI (NOUVEAU SYSTEME)
        // =========================
        p.closeInventory();
        MarketAdminGUI.open(p); // 🔥 IMPORTANT

        return true;
    }
}