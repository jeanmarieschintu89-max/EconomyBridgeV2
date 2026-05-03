package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        // 🔥 NORMALISATION ROBUSTE
        String clean = title.replaceAll("§.", "")
                .toLowerCase()
                .trim();

        // 🔥 MATCH FLEXIBLE
        if (!clean.contains("bourse")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() != e.getView().getTopInventory()) return;

        // 🔒 uniquement GUI
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        // 🔥 ON BLOQUE TOUJOURS
        e.setCancelled(true);

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType().isAir()) return;

        int slot = e.getRawSlot();

        // 🔙 retour menu
        if (slot == 4) {
            p.closeInventory();
            MainMenuGUI.open(p);
            return;
        }

        // 🔥 anti glitch
        if (e.isShiftClick() || e.isRightClick()) return;

        if (econ == null) {
            loadEconomy();
            if (econ == null) {
                p.sendMessage("§cErreur économie (Vault)");
                return;
            }
        }

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

        removeItems(p, mat, amount);

        econ.depositPlayer(p, gain);

        EconomyListener.log(p.getName(),
                "Vente " + id + " x" + amount,
                gain);

        p.sendMessage("§8────────────");
        p.sendMessage("§a✔ Vente effectuée");
        p.sendMessage("§7Quantité: §f" + amount);
        p.sendMessage("§7Gain: §a+" + gain + "€");
        p.sendMessage("§8────────────");

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
    }

    private int count(Player p, Material mat) {
        int total = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && item.getType() == mat) {
                total += item.getAmount();
            }
        }

        return total;
    }

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