package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;

public class MarketState {

    public static final Map<String, Double> stock = new HashMap<>();

    // 💎 ancien prix (pour trend)
    public static final Map<String, Double> lastPrice = new HashMap<>();

    // 📈 texte trend prêt à afficher
    public static final Map<String, String> trend = new HashMap<>();

}