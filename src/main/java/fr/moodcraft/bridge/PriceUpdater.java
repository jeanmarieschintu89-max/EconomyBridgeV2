package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Set;

public final class PriceUpdater {

    private PriceUpdater() {}

    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    public static void updateItem(String item) {

        if (!ALLOWED.contains(item)) return;

        double target = MarketEngine.getPrice(item);
        double base = MarketEngine.getBase(item);

        double maxStep = Math.max(2, base * 0.10);

        double raw = clampStep(item, target, maxStep);
        double clamped = round2(raw);

        // 🔥 Trend update
        TrendManager.updateTrend(item, clamped);

        Set<Shop> shops = ShopIndex.get(item);
        if (shops.isEmpty()) return;

        Iterator<Shop> it = shops.iterator();

        new BukkitRunnable() {
            @Override
            public void run() {

                int count = 0;

                while (it.hasNext() && count < 30) {
                    Shop s = it.next();

                    if (Math.abs(s.getPrice() - clamped) >= 0.01) {
                        s.setPrice(clamped);
                    }

                    count++;
                }

                if (!it.hasNext()) cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }

    public static void updateSingle(Shop s, String item) {

        if (!ALLOWED.contains(item)) return;

        double target = MarketEngine.getPrice(item);
        double base = MarketEngine.getBase(item);

        double maxStep = Math.max(2, base * 0.10);

        double raw = clampStep(item, target, maxStep);
        double clamped = round2(raw);

        if (Math.abs(s.getPrice() - clamped) < 0.01) return;

        s.setPrice(clamped);
    }

    private static double clampStep(String item, double target, double maxStep) {

        double last = MarketState.lastSent.getOrDefault(item, target);
        double diff = target - last;

        if (diff > maxStep) target = last + maxStep;
        else if (diff < -maxStep) target = last - maxStep;

        MarketState.lastSent.put(item, target);

        return target;
    }

    private static double round2(double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}