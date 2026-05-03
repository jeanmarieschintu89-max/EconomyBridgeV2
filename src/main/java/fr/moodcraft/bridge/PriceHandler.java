package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PriceHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ItemStack clicked = p.getOpenInventory().getItem(slot);
        if (clicked == null || clicked.getType().isAir()) return;

        // 🔙 retour menu
        if (slot == 4) {
            p.closeInventory();
            MainMenuGUI.open(p);
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

        // 🔥 REMOVE ITEMS
        removeItems(p, mat, amount);

        // 💰 paiement
        VaultHook.getEconomy().depositPlayer(p, gain);

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

        p.playSound(p.getLocation(),
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);

        // 📈 marché
        MarketEngine.applySell(id, amount);
        PriceUpdater.updateItem(id);
    }

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