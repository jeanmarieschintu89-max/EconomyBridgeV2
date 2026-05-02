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

        // 🔒 Vérifie le bon GUI
        if (title == null || !title.contains("Bourse")) return;

        // 🔒 Clique uniquement dans le GUI
        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) return;

        e.setCancelled(true);

        // 🔥 Anti bug double clic / shift
        if (e.isShiftClick() || e.isRightClick()) return;

        // 🔒 joueur uniquement
        if (!(e.getWhoClicked() instanceof Player p)) return;

        // 🔒 item valide
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;

        // 🔥 économie
        if (econ == null) {
            loadEconomy();
            if (econ == null) {
                p.sendMessage("§cErreur economie (Vault)");
                return;
            }
        }

        // 🔒 permission
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

        // 🔒 sécurité prix
        if (price <= 0) {
            p.sendMessage("§cPrix invalide");
            return;
        }

        double gain = round(amount * price);

        // 🔥 suppression SAFE
        removeItems(p, mat, amount);
        p.updateInventory();

        // 💰 paiement
        econ.depositPlayer(p, gain);

        // 📄 LOG
        TransactionLogger.log(p.getName(),
                "Vente " + id + " x" + amount,
                gain);

        // 💬 message
        p.sendMessage("§8────────────");
        p.sendMessage("§aVente effectuee");
        p.sendMessage("§7Quantite: §f" + amount);
        p.sendMessage("§7Gain: §a+" + gain + "€");
        p.sendMessage("§8────────────");

        // 📈 marché
        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
    }

    // =========================
    // 🔢 COMPTER ITEMS
    // =========================
    private int count(Player p, Material mat) {

        int total = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() != mat) continue;

            total += item.getAmount();
        }

        return total;
    }

    // =========================
    // 🧹 RETIRER ITEMS (SAFE)
    // =========================
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

    // =========================
    // 🔗 MAP ITEM → ID
    // =========================
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

    // =========================
    // 🔢 ARRONDI
    // =========================
    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}