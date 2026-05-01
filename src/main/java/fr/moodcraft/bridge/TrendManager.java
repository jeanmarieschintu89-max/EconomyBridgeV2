package fr.moodcraft.bridge;

public class TrendManager {

    public static void updateTrend(String item, double newPrice) {

        double old = MarketState.getPrice(item);

        double diff = newPrice - old;
        double percent = (old == 0) ? 0 : (diff / old) * 100;

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
    }

    public static String getTrend(String item) {
        return MarketState.trend.getOrDefault(item, "§7➡ stable");
    }
}