package fr.moodcraft.bridge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MarketState {

    private MarketState() {}

    // 💰 prix actuels
    public static final Map<String, Double> price = new ConcurrentHashMap<>();

    // 🧱 base
    public static final Map<String, Double> base = new ConcurrentHashMap<>();

    // 📦 stock simulé
    public static final Map<String, Double> stock = new ConcurrentHashMap<>();

    // 📊 tracking
    public static final Map<String, Double> buy = new ConcurrentHashMap<>();
    public static final Map<String, Double> sell = new ConcurrentHashMap<>();

    // 📈 tendance
    public static final Map<String, Double> lastPrice = new ConcurrentHashMap<>();
    public static final Map<String, String> trend = new ConcurrentHashMap<>();

    // 🔒 anti spike
    public static final Map<String, Double> lastSent = new ConcurrentHashMap<>();
}