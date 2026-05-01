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

        for (Shop s : shops) {
            add(s);
        }
    }

    public static Set<Shop> get(String item) {
        return INDEX.getOrDefault(item, Collections.emptySet());
    }

    public static void add(Shop s) {

        if (s == null || s.getItem() == null) return;

        String key = ItemNormalizer.normalize(
                s.getItem().getType().name().toLowerCase()
        );

        if (!PriceUpdater.ALLOWED.contains(key)) return;

        INDEX.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet())
             .add(s);
    }

    public static void remove(Shop s) {

        if (s == null || s.getItem() == null) return;

        String key = ItemNormalizer.normalize(
                s.getItem().getType().name().toLowerCase()
        );

        Set<Shop> set = INDEX.get(key);

        if (set != null) {
            set.removeIf(shop -> shop == null || shop.isDeleted());
        }
    }
}