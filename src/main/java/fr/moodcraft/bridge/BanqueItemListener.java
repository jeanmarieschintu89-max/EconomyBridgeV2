package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class BanqueItemListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        // =========================
        // 📦 LISTE ITEMS
        // =========================
        if (e.getView().getTitle().equals("§bItems Marché")) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;

            String item = ItemNormalizer.normalize(e.getCurrentItem().getType());
            if (item == null) return;

            BanqueItemGUI.open(p, item);
            return;
        }

        // =========================
        // ⚙️ CONFIG ITEM
        // =========================
        if (e.getView().getTitle().startsWith("§eConfig:")) {

            e.setCancelled(true);

            String item = e.getView().getTitle().replace("§eConfig: ", "");

            double impact = MarketState.impact.getOrDefault(item, 50.0);
            double activity = MarketState.activity.getOrDefault(item, 0.001);
            double rarity = MarketState.rarity.getOrDefault(item, 10.0);
            double weight = MarketState.weight.getOrDefault(item, 1.0);

            switch (e.getSlot()) {

                // =========================
                // 💥 IMPACT
                // =========================
                case 0 -> {
                    impact -= 1;
                    if (impact < 1) impact = 1;
                    MarketState.impact.put(item, impact);
                    p.sendMessage("§6Impact réduit → marché plus réactif");
                }

                case 1 -> {
                    impact += 1;
                    MarketState.impact.put(item, impact);
                    p.sendMessage("§eImpact augmenté → marché plus stable");
                }

                // =========================
                // 📊 ACTIVITY
                // =========================
                case 2 -> {
                    activity += 0.0002;
                    MarketState.activity.put(item, activity);
                    p.sendMessage("§aActivity ↑ → baisse plus forte avec stock");
                }

                case 3 -> {
                    activity -= 0.0002;
                    if (activity < 0) activity = 0;
                    MarketState.activity.put(item, activity);
                    p.sendMessage("§cActivity ↓ → moins d'effet du stock");
                }

                // =========================
                // 🌟 RARETÉ
                // =========================
                case 4 -> {
                    rarity += 1;
                    MarketState.rarity.put(item, rarity);
                    p.sendMessage("§6Rareté ↑ → prix monte plus vite si rare");
                }

                case 5 -> {
                    rarity -= 1;
                    if (rarity < 1) rarity = 1;
                    MarketState.rarity.put(item, rarity);
                    p.sendMessage("§eRareté ↓ → moins d'effet rareté");
                }

                // =========================
                // ⚖️ WEIGHT
                // =========================
                case 6 -> {
                    weight += 0.1;
                    MarketState.weight.put(item, weight);
                    p.sendMessage("§bWeight ↑ → influence stock augmentée");
                }

                case 7 -> {
                    weight -= 0.1;
                    if (weight < 0.1) weight = 0.1;
                    MarketState.weight.put(item, weight);
                    p.sendMessage("§cWeight ↓ → influence stock réduite");
                }

                // 🔙 retour
                case 8 -> BanqueItemListGUI.open(p);
            }

            // 🔥 recalcul marché instant
            MarketEngine.tick();

            // ⚡ update shops
            for (String i : MarketState.base.keySet()) {
                PriceUpdater.updateItem(i);
            }

            // 🔄 refresh GUI
            BanqueItemGUI.open(p, item);
        }
    }
}