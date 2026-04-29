package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Set;

public final class PriceUpdater {

    private PriceUpdater() {}

    // 🔒 WHITELIST (TES ITEMS ECO)
    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    public static void updateItem(String item) {

        // 🛑 BLOQUE ITEMS HORS ECO
        if (!ALLOWED.contains(item)) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        double base = getDouble("base." + item, target);
        double maxStep = Math.max(2, base * 0.10);
        double clamped = clampStep(item, target, maxStep);

        // 🔥 ANTI-SPAM
        double lastSend = getDouble("bridge.lastsend." + item, 0);
        if (Math.abs(lastSend - clamped) < 0.1) return;
        ch.njol.skript.variables.Variables.setVariable("bridge.lastsend." + item, clamped, null, false);

        Set<Shop> shops = ShopIndex.get(item);
        if (shops == null || shops.isEmpty()) return;

        Iterator<Shop> it = shops.iterator();

        new BukkitRunnable() {
            @Override
            public void run() {

                int count = 0;

                while (it.hasNext() && count < 10) { // ⚡ 10 shops par tick
                    Shop s = it.next();

                    if (Math.abs(s.getPrice() - clamped) >= 0.1) {
                        s.setPrice(clamped);
                    }

                    count++;
                }

                // ✅ stop propre
                if (!it.hasNext()) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);

        Main.getInstance().getLogger().info("Sync: " + item + " -> " + clamped);
    }

    public static void updateSingle(Shop s, String item) {

        // 🛑 BLOQUE ITEMS HORS ECO
        if (!ALLOWED.contains(item)) return;

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        double base = getDouble("base." + item, target);
        double maxStep = Math.max(2, base * 0.10);
        double clamped = clampStep(item, target, maxStep);

        if (Math.abs(s.getPrice() - clamped) < 0.1) return;

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
}