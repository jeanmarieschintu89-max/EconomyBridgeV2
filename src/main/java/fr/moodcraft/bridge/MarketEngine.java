package fr.moodcraft.bridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public final class MarketEngine {

    private static FileConfiguration cfg;

    public static void init(FileConfiguration config) {

        cfg = config;

        for (String item : PriceUpdater.ALLOWED) {

            double base = cfg.getDouble("base." + item, 10);

            MarketState.base.put(item, base);
            MarketState.price.put(item, base);
            MarketState.stock.put(item, 0.0);
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);
        }

        startAutoUpdate();
    }

    public static double getPrice(String item) {
        return MarketState.price.getOrDefault(item, 1.0);
    }

    public static double getBase(String item) {
        return MarketState.base.getOrDefault(item, 1.0);
    }

    public static void applyBuy(String item, int amount) {

        double price = getPrice(item);

        double impact = (amount * price) / 1000;
        impact *= cfg.getDouble("weight." + item, 1.0);

        price += impact / cfg.getDouble("impact." + item, 40);

        MarketState.buy.merge(item, (double) amount, Double::sum);
        MarketState.price.put(item, round(price));
    }

    public static void applySell(String item, int amount) {

        double price = getPrice(item);

        double impact = (amount * price) / 1000;
        impact *= cfg.getDouble("weight." + item, 1.0);

        price -= impact / cfg.getDouble("impact." + item, 40);

        MarketState.sell.merge(item, (double) amount, Double::sum);
        MarketState.stock.merge(item, (double) amount, Double::sum);

        MarketState.price.put(item, round(price));
    }

    private static void startAutoUpdate() {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (String item : PriceUpdater.ALLOWED) {

                    double price = getPrice(item);
                    double base = getBase(item);
                    double stock = MarketState.stock.getOrDefault(item, 0.0);

                    double activity = Math.sqrt(stock) * cfg.getDouble("activity." + item, 0.001);
                    price -= activity;

                    double diff = base - price;
                    price += diff * 0.006;

                    price = Math.max(base * 0.5, Math.min(base * 2.5, price));

                    MarketState.price.put(item, round(price));

                    TrendManager.updateTrend(item, price);
                    PriceUpdater.updateItem(item);
                }

                for (String item : PriceUpdater.ALLOWED) {
                    MarketState.stock.compute(item, (k, v) -> v * 0.85);
                }

            }
        }.runTaskTimer(Main.getInstance(), 20L * 45, 20L * 45);
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}