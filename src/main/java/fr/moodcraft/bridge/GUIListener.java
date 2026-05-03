package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        if (title == null) return;

        String clean = title.replaceAll("§.", "").trim();

        // =========================
        // 📊 BOURSE MINERAIS
        // =========================
        if (!clean.equalsIgnoreCase("Bourse Minerais")) return;

        if (e.getClickedInventory() == null) return;

        // 🔒 bloque uniquement le GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;

        int slot = e.getRawSlot();

        // =========================
        // 🔙 MENU PRINCIPAL
        // =========================
        if (slot == 4) {
            p.closeInventory();
            MainMenuGUI.open(p);
            return;
        }

        // 🔥 anti spam / geyser
        if (e.isShiftClick() || e.isRightClick()) return;

        // 🔥 économie safe
        if (econ == null) {
            loadEconomy();
            if (econ == null) {
                p.sendMessage("§cErreur économie (Vault)");
                return;
            }
        }

        // 🔒 permission
        if (!p.hasPermission("econ.use")) {
            p.sendMessage("§cAccès refusé");
            return;
        }

        Material mat = clicked.getType();
        String id = map(mat);

        if (id == null) return;

        int amount = count(p, mat);

        if (amount <= 0) {
            p.sendMessage("§cTu n'as rien à vendre");
            return;
        }

        double price = MarketEngine.getPrice(id);

        if (price <= 0) {
            p.sendMessage("§cPrix invalide");
            return;
        }

        double gain = round(amount * price);

        // =========================
        // 📦 REMOVE SAFE
        // =========================
        removeItems(p, mat, amount);

        // 💰 paiement
        econ.depositPlayer(p, gain);

        // 📄 LOG
        EconomyListener.log(p.getName(),
                "Vente " + id + " x" + amount,
                gain);

        // 💬 feedback
        p.sendMessage("§8────────────");
        p.sendMessage("§a✔ Vente effectuée");
        p.sendMessage("§7Quantité: §f" + amount);
        p.sendMessage("§7Gain: §a+" + gain + "€");
        p.sendMessage("§8────────────");

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        // 📈 marché
        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
    }

    // =========================
    // 🔢 COUNT
    // =========================
    private int count(Player p, Material mat) {

        int total = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && item.getType() == mat) {
                total += item.getAmount();
            }
        }

        return total;
    }

    // =========================
    // 🧹 REMOVE SAFE
    // =========================
    private void removeItems(Player p, Material mat, int amount) {

        int remaining = amount;

        for (ItemStack item : p.getInventory().getContents()) {

            if (item == null || item.getType() != mat) continue;

            int take = Math.min(item.getAmount(), remaining);

            item.setAmount(item.getAmount() - take);
            remaining -= take;

            if (remaining <= 0) break;
        }

        p.updateInventory();
    }

    // =========================
    // 🔗 MAP
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
    // 🔢 ROUND
    // =========================
    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}