package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractBuilder {

    public String item;
    public int amount = 1;
    public double price = 100;

    private static final Map<UUID, ContractBuilder> map = new HashMap<>();

    // =========================
    // 📥 GET
    // =========================
    public static ContractBuilder get(UUID uuid) {
        return map.get(uuid);
    }

    // =========================
    // ➕ CREATE
    // =========================
    public static ContractBuilder create(UUID uuid) {
        ContractBuilder b = new ContractBuilder();
        map.put(uuid, b);
        return b;
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(UUID uuid) {
        map.remove(uuid);
    }

    // =========================
    // 🔍 HAS (optionnel)
    // =========================
    public static boolean has(UUID uuid) {
        return map.containsKey(uuid);
    }
}