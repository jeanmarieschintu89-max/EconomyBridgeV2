package fr.moodcraft.bridge;

public final class MarketEngine {

    public static double getPrice(String item) {
        return MarketState.getPrice(item);
    }

    public static void applyBuy(String item, int amount) {
        MarketState.buy.merge(item, (double) amount, Double::sum);
    }

    public static void applySell(String item, int amount) {
        MarketState.sell.merge(item, (double) amount, Double::sum);
    }

    public static void tick() {

        for (String item : MarketState.base.keySet()) {

            double price = MarketState.getPrice(item);
            double base = MarketState.base.getOrDefault(item, price);
            double stock = MarketState.stock.getOrDefault(item, 0.0);
            double buy = MarketState.buy.getOrDefault(item, 0.0);
            double sell = MarketState.sell.getOrDefault(item, 0.0);

            double before = price;

            // =========================
            // 📉 ACTIVITY (CONFIG)
            // =========================
            double coef = MarketState.activity.getOrDefault(item, 0.001);
            double activity = Math.sqrt(stock) * coef;

            double maxDrop = price * 0.01;
            if (maxDrop < 0.05) maxDrop = 0.05;

            if (activity > maxDrop) activity = maxDrop;

            if (buy > 0 || sell > 0) {
                price -= activity;
            } else {
                price += base * 0.001;
            }

            // =========================
            // 🌟 RARITY
            // =========================
            double rare = MarketState.rarity.getOrDefault(item, 10.0);

            if (stock < rare) {
                price += base * 0.002;
            }

            // =========================
            // 📈 IMPACT
            // =========================
            double impact = (buy * 0.06) - (sell * 0.06) - (stock * 0.00015);

            if (impact > 1) impact = 1;
            if (impact < -1) impact = -1;

            double div = MarketState.impact.getOrDefault(item, 40.0);

            price += impact / div;

            // =========================
            // 🔄 RETOUR BASE
            // =========================
            price += (base - price) * 0.006;

            // =========================
            // 🧹 STOCK DECAY
            // =========================
            stock *= 0.85;

            if (stock > 10000) stock = 10000;

            MarketState.stock.put(item, stock);

            // =========================
            // 🧱 LIMITES
            // =========================
            double min = base * 0.5;
            double max = base * 2.5;

            if (price < min) price = min;
            if (price > max) price = max;

            if (price < 1) price = 1;

            price = round(price);

            MarketState.setPrice(item, price);

            // 📈 trend
            TrendManager.updateTrend(item, price);

            // 🔄 reset activité
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);

            // ⚡ sync shops
            PriceUpdater.updateItem(item);
        }
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}