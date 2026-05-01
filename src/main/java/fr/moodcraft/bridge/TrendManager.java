package fr.moodcraft.bridge;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrendManager {

    public static void updateTrend(String item, double newPrice) {

        double old = MarketState.lastPrice.getOrDefault(item, newPrice);

        double diff = newPrice - old;
        double percent = (old == 0) ? 0 : (diff / old) * 100;

        percent = round(percent);

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

    public static String getTrend(String item) {
        return MarketState.trend.getOrDefault(item, "§7➡ stable");
    }

    private static double round(double v) {
        return new BigDecimal(v)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}