package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;

public final class MarketState {

    public static final Map<String, Double> price = new HashMap<>();
    public static final Map<String, Double> base = new HashMap<>();
    public static final Map<String, Double> stock = new HashMap<>();
    public static final Map<String, Double> buy = new HashMap<>();
    public static final Map<String, Double> sell = new HashMap<>();
    public static final Map<String, String> trend = new HashMap<>();

    private MarketState() {}

    public static double getPrice(String item) {
        return price.getOrDefault(item, base.getOrDefault(item, 0.0));
    }

    public static void setPrice(String item, double value) {
        price.put(item, value);
    }
}