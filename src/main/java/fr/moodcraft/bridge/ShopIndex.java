package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ShopIndex {

    private static final Map<String, Set<Shop>> INDEX = new ConcurrentHashMap<>();

    private ShopIndex() {}

    public static void rebuild() {

        INDEX.clear();

        Collection<Shop> shops = QuickShopAPI.getInstance()
                .getShopManager()
                .getAllShops();

        for (Shop shop : shops) {

            if (shop == null) continue;

            String key = normalize(shop.getItem().getType().name().toLowerCase());

            // ✅ FIX ALLOWED
            if (!PriceUpdater.ALLOWED.contains(key)) continue;

            INDEX.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet()).add(shop);
        }
    }

    public static Set<Shop> get(String item) {
        return INDEX.getOrDefault(item, Collections.emptySet());
    }

    private static String normalize(String mat) {

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