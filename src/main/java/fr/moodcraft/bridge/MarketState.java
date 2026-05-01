package fr.moodcraft.bridge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MarketState {

    private MarketState() {}

    public static final Map<String, Double> price = new ConcurrentHashMap<>();
    public static final Map<String, Double> base = new ConcurrentHashMap<>();
    public static final Map<String, Double> stock = new ConcurrentHashMap<>();

    public static final Map<String, Double> buy = new ConcurrentHashMap<>();
    public static final Map<String, Double> sell = new ConcurrentHashMap<>();

    public static final Map<String, Double> lastPrice = new ConcurrentHashMap<>();
    public static final Map<String, String> trend = new ConcurrentHashMap<>();

    public static final Map<String, Double> lastSent = new ConcurrentHashMap<>();
}