package fr.moodcraft.bridge;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractBuilder {

    // =========================
    // 📦 DONNÉES CONTRAT
    // =========================
    public String item;
    public ItemStack itemStack; // icône réelle
    public int amount = 1;
    public double price = 100;

    private static final Map<UUID, ContractBuilder> map = new HashMap<>();

    // =========================
    // 🔥 GET SAFE (TOUJOURS UTILISER CELUI-CI)
    // =========================
    public static ContractBuilder get(UUID uuid) {
        return map.computeIfAbsent(uuid, u -> new ContractBuilder());
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(UUID uuid) {
        map.remove(uuid);
    }

    // =========================
    // 🔍 HAS
    // =========================
    public static boolean has(UUID uuid) {
        return map.containsKey(uuid);
    }

    // =========================
    // 🧹 RESET (optionnel propre)
    // =========================
    public static void reset(UUID uuid) {
        map.put(uuid, new ContractBuilder());
    }
}