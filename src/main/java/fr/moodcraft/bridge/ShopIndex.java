package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ShopIndex {

    // item normalisé -> shops
    private static final Map<String, Set<Shop>> INDEX = new ConcurrentHashMap<>();

    private ShopIndex() {}

    public static void rebuild() {
        INDEX.clear();
        Collection<Shop> shops = QuickShopAPI.getInstance().getShopManager().getAllShops();

        for (Shop s : shops) {

            String key = normalize(s.getItem().getType().name().toLowerCase());

            // 🛑 FILTRE ULTRA IMPORTANT (ANTI 0.10)
            if (!PriceUpdater.ALLOWED.contains(key)) {
                continue;
            }

            INDEX.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet()).add(s);
        }
    }

    public static Set<Shop> get(String item) {
        return INDEX.getOrDefault(item, Collections.emptySet());
    }

    public static void add(Shop s) {
        String key = normalize(s.getItem().getType().name().toLowerCase());

        // 🛑 FILTRE
        if (!PriceUpdater.ALLOWED.contains(key)) return;

        INDEX.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet()).add(s);
    }

    public static void remove(Shop s) {
        String key = normalize(s.getItem().getType().name().toLowerCase());
        Set<Shop> set = INDEX.get(key);
        if (set != null) set.remove(s);
    }

    public static String normalize(String mat) {
        if (mat.contains("diamond")) return "diamond";
        if (mat.contains("emerald")) return "emerald";
        if (mat.contains("gold")) return "gold";
        if (mat.contains("iron")) return "iron";
        if (mat.contains("copper")) return "copper";
        if (mat.contains("coal") || mat.contains("charcoal")) return "coal";
        if (mat.contains("lapis")) return "lapis";
        if (mat.contains("redstone")) return "redstone";
        if (mat.contains("quartz")) return "quartz";
        if (mat.contains("amethyst")) return "amethyst";
        if (mat.contains("netherite")) return "netherite";
        if (mat.contains("glowstone")) return "glowstone";
        return mat;
    }
}