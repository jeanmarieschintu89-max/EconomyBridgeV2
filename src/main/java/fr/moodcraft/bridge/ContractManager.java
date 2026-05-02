package fr.moodcraft.bridge;

import java.util.*;

public class ContractManager {

    private static final Map<Integer, Contract> contracts = new HashMap<>();
    private static int idCounter = 0;

    // =========================
    // 🔥 CREATE
    // =========================
    public static int create(UUID owner, String item, int amount, double price) {

        int id = ++idCounter;

        Contract c = new Contract(owner, item, amount, price);
        c.id = id;
        c.status = Contract.Status.PENDING; // ⚠️ adapte à ton enum

        contracts.put(id, c);

        return id;
    }

    // =========================
    // 🔥 LISTE
    // =========================
    public static List<Contract> getOpenContracts() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.PENDING)
                .toList();
    }

    // =========================
    // 🔥 BACKWARD COMPAT
    // =========================

    // 👉 ancien système (book sign)
    public static Contract getOpen() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.PENDING)
                .findFirst()
                .orElse(null);
    }

    // 👉 ancien système livraison
    public static Contract getByWorker(UUID uuid) {
        return contracts.values().stream()
                .filter(c -> uuid.equals(c.worker) && c.status == Contract.Status.ACCEPTED)
                .findFirst()
                .orElse(null);
    }

    // =========================
    // 🔍 GET
    // =========================
    public static Contract get(int id) {
        return contracts.get(id);
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(int id) {
        contracts.remove(id);
    }
}