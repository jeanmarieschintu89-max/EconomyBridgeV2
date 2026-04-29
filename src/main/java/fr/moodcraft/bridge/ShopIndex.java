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

    private String normalize(String mat) {

        switch (mat) {
            case "diamond": return "diamond";
            case "emerald": return "emerald";
            case "gold_ingot": return "gold";
            case "iron_ingot": return "iron";
            case "copper_ingot": return "copper";
            case "coal":
            case "charcoal": return "coal";
            case "lapis_lazuli": return "lapis";
            case "redstone": return "redstone";
            case "quartz": return "quartz";
            case "amethyst_shard": return "amethyst";
            case "netherite_ingot": return "netherite";
            case "glowstone_dust": return "glowstone";
        }

        return mat;
    }
}