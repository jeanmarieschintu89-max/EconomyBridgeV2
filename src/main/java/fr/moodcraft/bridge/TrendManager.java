package fr.moodcraft.bridge;

public class TrendManager {

    public static void updateTrend(String item, double newPrice) {

        double old = MarketState.lastPrice.getOrDefault(item, newPrice);

        double diff = newPrice - old;
        double percent = old == 0 ? 0 : (diff / old) * 100;

        percent = Math.round(percent * 100.0) / 100.0;

        String result;

        if (percent > 0) {
            result = percent > 5 ? "§2⬆ +" + percent + "%" : "§a⬆ +" + percent + "%";
        } else if (percent < 0) {
            result = percent < -5 ? "§4⬇ " + percent + "%" : "§c⬇ " + percent + "%";
        } else {
            result = "§7➡ stable";
        }

        MarketState.trend.put(item, result);
        MarketState.lastPrice.put(item, newPrice);
    }
}