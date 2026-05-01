package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ShopIndex {

    private static final Map<String, Set<Shop>> INDEX = new ConcurrentHashMap<>();

    private ShopIndex() {}

    // 🔁 rebuild au démarrage
    public static void rebuild() {

        INDEX.clear();

        Collection<Shop> shops = QuickShopAPI.getInstance()
                .getShopManager()
                .getAllShops();

        for (Shop shop : shops) {
            add(shop);
        }
    }

    // ➕ ajout dynamique
    public static void add(Shop shop) {

        if (shop == null || shop.getItem() == null) return;

        String key = ItemNormalizer.normalize(shop.getItem().getType());

        if (key == null) return;
        if (!PriceUpdater.ALLOWED.contains(key)) return;

        INDEX.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet()).add(shop);
    }

    // ➖ suppression dynamique
    public static void remove(Shop shop) {

        if (shop == null || shop.getItem() == null) return;

        String key = ItemNormalizer.normalize(shop.getItem().getType());

        if (key == null) return;

        Set<Shop> set = INDEX.get(key);
        if (set != null) {
            set.remove(shop);
        }
    }

    // 📦 récupérer les shops d’un item
    public static Set<Shop> get(String item) {
        return INDEX.getOrDefault(item, Collections.emptySet());
    }
}