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

    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    public static void updateItem(String item) {

        if (!ALLOWED.contains(item)) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        double base = getDouble("base." + item, target);
        double maxStep = Math.max(2, base * 0.10);

        double raw = clampStep(item, target, maxStep);
        double clamped = round2(raw);

        double lastSend = getDouble("bridge.lastsend." + item, 0);
        if (Math.abs(lastSend - clamped) < 0.009) return;

        ch.njol.skript.variables.Variables.setVariable("bridge.lastsend." + item, clamped, null, false);

        Set<Shop> shops = ShopIndex.get(item);
        if (shops == null || shops.isEmpty()) return;

        Iterator<Shop> it = shops.iterator();

        // ⚡ UPDATE OPTIMISÉ (batch)
        new BukkitRunnable() {
            @Override
            public void run() {

                int count = 0;

                while (it.hasNext() && count < 25) {
                    Shop s = it.next();

                    if (Math.abs(s.getPrice() - clamped) >= 0.009) {
                        s.setPrice(clamped);
                    }

                    count++;
                }

                if (!it.hasNext()) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);

        Main.getInstance().getLogger().info("⚡ Sync: " + item + " -> " + clamped);
    }

    public static void updateSingle(Shop s, String item) {

        if (!ALLOWED.contains(item)) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        double base = getDouble("base." + item, target);
        double maxStep = Math.max(2, base * 0.10);

        double raw = clampStep(item, target, maxStep);
        double clamped = round2(raw);

        if (Math.abs(s.getPrice() - clamped) < 0.009) return;

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            s.setPrice(clamped);
        });
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

    // 💎 ARRONDI PROPRE
    private static double round2(double value) {
        return new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}