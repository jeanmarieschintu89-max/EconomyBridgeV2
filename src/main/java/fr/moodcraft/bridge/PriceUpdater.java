package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.Bukkit;

import java.util.Set;

public final class PriceUpdater {

    private PriceUpdater() {}

    public static void updateItem(String item) {

        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        if (!(v instanceof Number n)) return;

        double target = n.doubleValue();

        double base = getDouble("base." + item, target);

        // 🔥 variation contrôlée
        double maxStep = Math.max(2, base * 0.10);
        double clamped = clampStep(item, target, maxStep);

        Set<Shop> shops = ShopIndex.get(item);
        if (shops == null || shops.isEmpty()) return;

        // 🔒 MAIN THREAD OBLIGATOIRE
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

            for (Shop s : shops) {

                if (Math.abs(s.getPrice() - clamped) < 0.1) continue;

                s.setPrice(clamped);
            }
        });

        Main.getInstance().getLogger().info("Sync: " + item + " -> " + clamped + " (" + shops.size() + " shops)");
    }

    public static void updateSingle(Shop s, String item) {

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