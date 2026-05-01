package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public final class MarketEngine {

    private static FileConfiguration cfg;

    public static void init(FileConfiguration config) {

        cfg = config;

        for (String item : cfg.getConfigurationSection("base").getKeys(false)) {

            double base = cfg.getDouble("base." + item);

            MarketState.base.put(item, base);
            MarketState.price.put(item, base);
            MarketState.stock.put(item, 0.0);
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);
        }

        start();
    }

    public static double getPrice(String item) {
        return MarketState.price.getOrDefault(item, 1.0);
    }

    public static void applyBuy(String item, int amount) {
        MarketState.buy.merge(item, (double) amount, Double::sum);
    }

    public static void applySell(String item, int amount) {
        MarketState.sell.merge(item, (double) amount, Double::sum);
        MarketState.stock.merge(item, (double) amount, Double::sum);
    }

    private static void start() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (String item : MarketState.price.keySet()) {

                    double price = getPrice(item);
                    double base = MarketState.base.get(item);
                    double before = price;
                    double stock = MarketState.stock.get(item);

                    double coef = cfg.getDouble("activity." + item, 0.001);
                    double activity = Math.sqrt(stock) * coef;

                    double maxDrop = Math.max(price * 0.01, 0.05);
                    activity = Math.min(activity, maxDrop);

                    boolean active =
                            MarketState.buy.get(item) > 0 ||
                            MarketState.sell.get(item) > 0;

                    if (active) price -= activity;
                    else price += base * 0.001;

                    double rarity = cfg.getDouble("rarity." + item, 10);

                    if (stock < rarity) {
                        price += base * 0.002;
                    }

                    double impact =
                            (MarketState.buy.get(item) * 0.06)
                          - (MarketState.sell.get(item) * 0.06)
                          - (stock * 0.00015);

                    impact = clamp(impact, -1, 1);

                    price += impact / cfg.getDouble("impact." + item, 40);

                    double diff = price - before;
                    double limit = before * 0.05;

                    if (diff > limit) price = before + limit;
                    if (diff < -limit) price = before - limit;

                    price += (base - price) * 0.006;

                    stock *= 0.85;
                    if (stock > 10000) stock = 10000;

                    if (price < 1) price = 1;

                    price = clamp(price, base * 0.5, base * 2.5);

                    price = round(price);

                    MarketState.price.put(item, price);
                    MarketState.stock.put(item, stock);

                    TrendManager.updateTrend(item, price);

                    PriceUpdater.updateItem(item);
                }

            }
        }.runTaskTimer(Main.getInstance(), 20L * 45, 20L * 45);
    }

    private static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}