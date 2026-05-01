package fr.moodcraft.bridge;

public final class MarketEngine {

    public static void tick() {

        for (String item : PriceUpdater.ALLOWED) {

            double price = MarketState.getPrice(item);
            double base = MarketState.base.getOrDefault(item, price);
            double stock = MarketState.stock.getOrDefault(item, 0.0);
            double buy = MarketState.buy.getOrDefault(item, 0.0);
            double sell = MarketState.sell.getOrDefault(item, 0.0);

            // 📉 activité
            double activity = Math.sqrt(stock) * 0.002;

            if (buy > 0 || sell > 0) {
                price -= activity;
            } else {
                price += base * 0.001;
            }

            // 📈 impact
            double impact = (buy - sell) * 0.05 - stock * 0.00015;
            price += impact;

            // 🔄 retour vers base
            price += (base - price) * 0.006;

            // 🧊 limites
            double min = base * 0.5;
            double max = base * 2.5;

            if (price < min) price = min;
            if (price > max) price = max;

            price = round(price);

            MarketState.setPrice(item, price);

            TrendManager.updateTrend(item, price);

            // reset
            MarketState.buy.put(item, 0.0);
            MarketState.sell.put(item, 0.0);

            // sync shop
            PriceUpdater.updateItem(item);
        }
    }

    private static double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}