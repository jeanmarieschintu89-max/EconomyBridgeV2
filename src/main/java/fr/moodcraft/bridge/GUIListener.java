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
        loadEconomy();
    }

    private void loadEconomy() {
        if (econ != null) return;

        var rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            econ = rsp.getProvider();
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null || !title.contains("Bourse")) return;

        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        if (econ == null) {
            loadEconomy();
            if (econ == null) {
                p.sendMessage("§cErreur economie");
                return;
            }
        }

        if (!p.hasPermission("econ.use")) {
            p.sendMessage("§cAcces refuse");
            return;
        }

        Material mat = e.getCurrentItem().getType();
        String id = map(mat);
        if (id == null) return;

        int amount = count(p, mat);

        if (amount <= 0) {
            p.sendMessage("§cTu n'as rien a vendre");
            return;
        }

        double price = MarketEngine.getPrice(id);
        double gain = round(amount * price);

        // 🔥 suppression SAFE
        removeItems(p, mat, amount);

        econ.depositPlayer(p, gain);

        TransactionLogger.log(p.getName(),
                "Vente " + id + " x" + amount,
                gain);

        p.sendMessage("§8────────────");
        p.sendMessage("§aVente effectuee");
        p.sendMessage("§7Quantite: §f" + amount);
        p.sendMessage("§7Gain: §a+" + gain + "€");
        p.sendMessage("§8────────────");

        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
    }

    // 🔥 FIX COUNT
    private int count(Player p, Material mat) {
        int total = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != mat) continue;

            total += item.getAmount();
        }

        return total;
    }

    // 🔥 FIX REMOVE
    private void removeItems(Player p, Material mat, int amount) {

        int toRemove = amount;

        for (ItemStack item : p.getInventory().getContents()) {

            if (item == null) continue;
            if (item.getType() != mat) continue;

            int remove = Math.min(item.getAmount(), toRemove);

            item.setAmount(item.getAmount() - remove);

            toRemove -= remove;

            if (toRemove <= 0) break;
        }
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