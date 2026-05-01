package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public final class MarketEngine {

    private static FileConfiguration cfg;

    private MarketEngine() {}

    public static void init(FileConfiguration config) {
        cfg = config;

        for (String item : PriceUpdater.ALLOWED) {

            double base = cfg.getDouble("base." + item, 10);
            double price = cfg.getDouble("price." + item, base);

            MarketState.base.put(item, base);
            MarketState.price.put(item, price);
            MarketState.stock.put(item, 0.0);
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);
        }

        startAutoUpdate();
    }

    // =========================
    // 💰 GETTERS
    // =========================

    public static double getPrice(String item) {
        return MarketState.price.getOrDefault(item, 1.0);
    }

    public static double getBase(String item) {
        return MarketState.base.getOrDefault(item, 1.0);
    }

    // =========================
    // 📈 ACHAT
    // =========================

    public static void applyBuy(String item, int amount) {

        double price = getPrice(item);

        double impact = (amount * price) / 1000;
        impact *= getWeight(item);

        impact = clamp(impact, -2, 2);

        double div = getImpactDiv(item);

        price += impact / div;

        MarketState.buy.merge(item, (double) amount, Double::sum);

        MarketState.price.put(item, round(price));
    }

    // =========================
    // 📉 VENTE
    // =========================

    public static void applySell(String item, int amount) {

        double price = getPrice(item);

        double impact = (amount * price) / 1000;
        impact *= getWeight(item);

        impact = clamp(impact, -2, 2);

        double div = getImpactDiv(item);

        price -= impact / div;

        MarketState.sell.merge(item, (double) amount, Double::sum);
        MarketState.stock.merge(item, (double) amount, Double::sum);

        MarketState.price.put(item, round(price));
    }

    // =========================
    // 🔁 AUTO UPDATE (ex Skript)
    // =========================

    private static void startAutoUpdate() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (String item : PriceUpdater.ALLOWED) {

                    double price = getPrice(item);
                    double base = getBase(item);
                    double stock = MarketState.stock.getOrDefault(item, 0.0);

                    // 📉 activité
                    double coef = getActivity(item);
                    double activity = Math.sqrt(stock) * coef;

                    double maxDrop = Math.max(price * 0.01, 0.05);
                    activity = Math.min(activity, maxDrop);

                    if (hasActivity(item)) {
                        price -= activity;
                    } else {
                        price += base * 0.001;
                    }

                    // 📈 impact trades
                    double impact =
                            (MarketState.buy.getOrDefault(item, 0.0) * 0.06)
                          - (MarketState.sell.getOrDefault(item, 0.0) * 0.06)
                          - (stock * 0.00015);

                    impact = clamp(impact, -1, 1);

                    price += impact / getImpactDiv(item);

                    // 🔄 retour base
                    double diff = base - price;
                    price += diff * 0.006;

                    // 🧊 limites
                    price = clamp(price, base * 0.5, base * 2.5);
                    if (price < 1) price = 1;

                    MarketState.price.put(item, round(price));

                    // 📈 trend
                    TrendManager.updateTrend(item, price);

                    // ⚡ sync shop
                    PriceUpdater.updateItem(item);
                }

                // 🧹 decay
                for (String item : PriceUpdater.ALLOWED) {
                    MarketState.stock.compute(item, (k, v) -> v * 0.85);
                }

            }
        }.runTaskTimer(Main.getInstance(), 20L * 45, 20L * 45);
    }

    // =========================
    // ⚙️ CONFIG HELPERS
    // =========================

    private static double getWeight(String item) {
        return cfg.getDouble("weight." + item, 1.0);
    }

    private static double getImpactDiv(String item) {
        return cfg.getDouble("impact." + item, 40);
    }

    private static double getActivity(String item) {
        return cfg.getDouble("activity." + item, 0.001);
    }

    private static boolean hasActivity(String item) {
        return MarketState.buy.getOrDefault(item, 0.0) > 0 ||
               MarketState.sell.getOrDefault(item, 0.0) > 0;
    }

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}