package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals("§6🏪 Marché (€)")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getCurrentItem() == null) return;

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

            // 🆕 anti whale
            double mult = 1;
            if (amount > 512) mult = 0.6;
            else if (amount > 256) mult = 0.8;

            gain *= mult;

            // 🆕 taxe config
            double taxRate = Main.getInstance().getConfig().getDouble("tax", 20);
            double tax = gain * taxRate / 100;
            double finalGain = gain - tax;

            p.getInventory().removeItem(new ItemStack(mat, amount));

            p.sendMessage("§a✔ Vente réussie");
            p.sendMessage("§6💰 Brut: " + gain);
            p.sendMessage("§cTaxe: -" + tax);
            p.sendMessage("§aNet: " + finalGain);

            p.getServer().dispatchCommand(
                    p.getServer().getConsoleSender(),
                    "eco give " + p.getName() + " " + finalGain
            );

            MarketEngine.applySell(id, amount);
            PriceUpdater.updateItem(id);
        }

        // =========================
        // 🔵 ACHAT
        // =========================
        if (click.isRightClick()) {

            int amount = 64;

            double price = MarketEngine.getPrice(id);
            double cost = price * amount;

            p.getServer().dispatchCommand(
                    p.getServer().getConsoleSender(),
                    "eco take " + p.getName() + " " + cost
            );

            p.getInventory().addItem(new ItemStack(mat, amount));

            p.sendMessage("§a✔ Achat réussi");
            p.sendMessage("§cCoût: -" + cost);

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
}