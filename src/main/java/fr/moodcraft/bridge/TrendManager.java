package fr.moodcraft.bridge;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrendManager {

    private static final double MIN_PERCENT = 0.05; // 🔥 seuil anti bruit

    public static void updateTrend(String item, double newPrice) {

        double old = MarketState.lastPrice.getOrDefault(item, newPrice);

        // 🛑 évite recalcul inutile
        if (Math.abs(newPrice - old) < 0.0001) return;

        double diff = newPrice - old;
        double percent = (old == 0) ? 0 : (diff / old) * 100;

        percent = round(percent);

        String result;

        // 🧊 zone neutre (anti spam visuel)
        if (Math.abs(percent) < MIN_PERCENT) {
            result = "§7➡ stable";
        }

        // 📈 hausse
        else if (percent > 0) {
            result = percent > 5
                    ? "§2⬆ +" + percent + "%"
                    : "§a⬆ +" + percent + "%";
        }

        // 📉 baisse
        else {
            result = percent < -5
                    ? "§4⬇ " + percent + "%"
                    : "§c⬇ " + percent + "%";
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