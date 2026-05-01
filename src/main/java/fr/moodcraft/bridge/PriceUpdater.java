package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Set;

public final class PriceUpdater {

    private PriceUpdater() {}

    // ✅ FIX ALLOWED
    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    public static void updateItem(String item) {

        if (!ALLOWED.contains(item)) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double price = round(n.doubleValue());

        Set<Shop> shops = ShopIndex.get(item);
        if (shops.isEmpty()) return;

        Iterator<Shop> it = shops.iterator();

        new BukkitRunnable() {
            @Override
            public void run() {

                int count = 0;

                while (it.hasNext() && count < 25) {
                    Shop shop = it.next();

                    if (shop == null) continue;

                    if (Math.abs(shop.getPrice() - price) >= 0.01) {
                        shop.setPrice(price);
                    }

                    count++;
                }

                if (!it.hasNext()) cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }

    public static void updateSingle(Shop shop, String item) {

        if (!ALLOWED.contains(item)) return;
        if (shop == null) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double price = round(n.doubleValue());

        if (Math.abs(shop.getPrice() - price) < 0.01) return;

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            shop.setPrice(price);
        });
    }

    private static double round(double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}