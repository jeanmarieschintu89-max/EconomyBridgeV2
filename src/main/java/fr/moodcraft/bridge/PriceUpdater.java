package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PriceUpdater {

    private PriceUpdater() {}

    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    private static final Map<String, Long> cooldown = new HashMap<>();

    public static void updateItem(String item) {

        if (!ALLOWED.contains(item)) return;

        long now = System.currentTimeMillis();
        long cd = Main.getInstance().getConfig().getLong("sync.cooldown-ms", 200);

        if (cooldown.containsKey(item)) {
            if (now - cooldown.get(item) < cd) return;
        }

        cooldown.put(item, now);

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        var config = Main.getInstance().getConfig();

        double base = config.getDouble("prices.base." + item, target);
        double maxStep = Math.max(2, base * 0.10);

        // 📦 impact stock
        double stock = MarketState.stock.getOrDefault(item, 0.0);
        target -= stock * 0.00015;

        double raw = clampStep(item, target, maxStep);
        double clamped = round2(raw);

        double lastSend = getDouble("bridge.lastsend." + item, 0);
        if (Math.abs(lastSend - clamped) < 0.009) return;

        ch.njol.skript.variables.Variables.setVariable("bridge.lastsend." + item, clamped, null, false);

        // 📈 trend direct
        TrendManager.updateTrend(item, clamped);

        Set<Shop> shops = ShopIndex.get(item);
        if (shops == null || shops.isEmpty()) return;

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            for (Shop s : shops) {
                if (Math.abs(s.getPrice() - clamped) >= 0.009) {
                    s.setPrice(clamped);
                }
            }
        });
    }

    // 🔥 NOUVEAU : accessible depuis Skript
    public static String getTrend(String item) {
        return TrendManager.getTrend(item);
    }

    private static double getDouble(String path, double def) {
        Object v = ch.njol.skript.variables.Variables.getVariable(path, null, false);
        return (v instanceof Number n) ? n.doubleValue() : def;
    }

    private static double clampStep(String item, double target, double maxStep) {

        double last = getDouble("bridge.last." + item, target);
        double diff = target - last;

        if (diff > maxStep) target = last + maxStep;
        else if (diff < -maxStep) target = last - maxStep;

        ch.njol.skript.variables.Variables.setVariable("bridge.last." + item, target, null, false);

        return target;
    }

    private static double round2(double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}