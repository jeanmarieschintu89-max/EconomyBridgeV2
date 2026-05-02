package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

        String title = e.getView().getTitle();

        // 🔥 BEDROCK SAFE
        if (title == null || !title.contains("Bourse")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        if (!p.hasPermission("econ.use")) {
            p.sendMessage("§cAcces refuse");
            return;
        }

        if (econ == null) {
            p.sendMessage("§cErreur economie");
            return;
        }

        Material mat = e.getCurrentItem().getType();
        String id = map(mat);

        if (id == null) return;

        // =========================
        // 🔥 VENTE UNIQUE
        // =========================

        int amount = count(p, mat);

        if (amount <= 0) {
            p.sendMessage("§cRien a vendre");
            return;
        }

        double price = MarketEngine.getPrice(id);
        double gain = amount * price;

        // 📉 pénalité volume
        double mult = 1;
        if (amount > 512) mult = 0.6;
        else if (amount > 256) mult = 0.8;

        gain *= mult;

        // 💸 taxe
        double taxRate = Main.getInstance().getConfig().getDouble("tax", 20);
        double tax = gain * taxRate / 100;
        double finalGain = gain - tax;

        double brut = round(gain);
        double taxe = round(tax);
        double net = round(finalGain);

        // 📦 remove + pay
        p.getInventory().removeItem(new ItemStack(mat, amount));
        econ.depositPlayer(p, net);

        // 📄 LOG
        TransactionLogger.log(p.getName(),
                "Vente " + id + " x" + amount,
                net);

        // 📢 message clean
        p.sendMessage("§8────────────");
        p.sendMessage("§a✔ Vente effectuée");
        p.sendMessage("§7Quantite: §f" + amount);
        p.sendMessage("§7Brut: §f" + brut + "€");
        p.sendMessage("§7Taxe: §c-" + taxe + "€");
        p.sendMessage("§7Gain: §a+" + net + "€");
        p.sendMessage("§8────────────");

        // 📈 marché
        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
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