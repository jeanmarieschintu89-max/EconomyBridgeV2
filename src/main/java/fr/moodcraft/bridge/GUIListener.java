package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private static Economy econ;

    public GUIListener() {
        if (econ == null) {
            var rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                econ = rsp.getProvider();
            }
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6🏪 Marché (€)")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

        if (!p.hasPermission("econ.use")) {
            p.sendMessage("§c❌ Accès refusé.");
            return;
        }

        if (econ == null) {
            p.sendMessage("§c❌ Erreur économie (Vault manquant)");
            return;
        }

        Material mat = e.getCurrentItem().getType();
        String id = map(mat);

        if (id == null) return;

        ClickType click = e.getClick();

        // =========================
        // 🟢 VENTE
        // =========================
        if (click.isLeftClick()) {

            int amount = count(p, mat);

            if (amount <= 0) {
                p.sendMessage("§c❌ Rien à vendre");
                return;
            }

            double price = MarketEngine.getPrice(id);
            double gain = amount * price;

            double mult = 1;
            if (amount > 512) mult = 0.6;
            else if (amount > 256) mult = 0.8;

            gain *= mult;

            double taxRate = Main.getInstance().getConfig().getDouble("tax", 20);
            double tax = gain * taxRate / 100;
            double finalGain = gain - tax;

            double brut = round(gain);
            double taxe = round(tax);
            double net = round(finalGain);

            p.getInventory().removeItem(new ItemStack(mat, amount));
            econ.depositPlayer(p, net);

            // 📄 LOG VENTE 🔥
            TransactionLogger.log(p.getName(),
                    "Vente " + id + " x" + amount,
                    net);

            p.sendMessage("§8▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
            p.sendMessage("§a✔ Vente réussie");
            p.sendMessage("§7• Brut: §f" + brut + "€");
            p.sendMessage("§7• Taxe: §c-" + taxe + "€");
            p.sendMessage("§7• Gain: §a+" + net + "€");
            p.sendMessage("§8▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

            MarketEngine.applySell(id, amount);
            PriceUpdater.updateItem(id);
        }

        // =========================
        // 🔵 ACHAT
        // =========================
        if (click.isRightClick()) {

            int amount = 64;

            double price = MarketEngine.getPrice(id);
            double cost = round(price * amount);

            if (econ.getBalance(p) < cost) {
                p.sendMessage("§c❌ Pas assez d'argent");
                return;
            }

            econ.withdrawPlayer(p, cost);
            p.getInventory().addItem(new ItemStack(mat, amount));

            // 📄 LOG ACHAT 🔥
            TransactionLogger.log(p.getName(),
                    "Achat " + id + " x" + amount,
                    cost);

            p.sendMessage("§a✔ Achat réussi");
            p.sendMessage("§cCoût: -" + cost + "€");

            MarketEngine.applyBuy(id, amount);
            PriceUpdater.updateItem(id);
        }
    }

    private int count(Player p, Material mat) {
        int total = 0;
        for (ItemStack item : p.getInventory()) {
            if (item != null && item.getType() == mat) {
                total += item.getAmount();
            }
        }
        return total;
    }

    private String map(Material mat) {
        switch (mat) {
            case DIAMOND: return "diamond";
            case EMERALD: return "emerald";
            case GOLD_INGOT: return "gold";
            case IRON_INGOT: return "iron";
            case COPPER_INGOT: return "copper";
            case COAL: return "coal";
            case LAPIS_LAZULI: return "lapis";
            case REDSTONE: return "redstone";
            case QUARTZ: return "quartz";
            case AMETHYST_SHARD: return "amethyst";
            case NETHERITE_INGOT: return "netherite";
            case GLOWSTONE_DUST: return "glowstone";
        }
        return null;
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}