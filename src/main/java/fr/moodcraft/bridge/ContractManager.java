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
        c.status = Contract.Status.OPEN;

        contracts.put(id, c);

        return id;
    }

    // =========================
    // 📜 LISTE DES CONTRATS OUVERTS
    // =========================
    public static List<Contract> getOpenContracts() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.OPEN)
                .toList();
    }

    // =========================
    // 📦 TOUS LES CONTRATS (GUI)
    // =========================
    public static List<Contract> getAll() {
        return new ArrayList<>(contracts.values());
    }

    // =========================
    // 🧠 BACKWARD COMPAT
    // =========================

    public static Contract getOpen() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.OPEN)
                .findFirst()
                .orElse(null);
    }

    public static Contract getByWorker(UUID uuid) {
        return contracts.values().stream()
                .filter(c -> uuid.equals(c.worker) && c.status == Contract.Status.ACCEPTED)
                .findFirst()
                .orElse(null);
    }

    // =========================
    // 🔍 GET PAR ID
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

    // =========================
    // 🛑 SÉCURITÉ BONUS
    // =========================

    // empêche double accept
    public static boolean isAlreadyTaken(int id) {
        Contract c = contracts.get(id);
        return c != null && c.status != Contract.Status.OPEN;
    }

    // empêche multi contrats par joueur
    public static boolean hasActiveContract(UUID player) {
        return contracts.values().stream()
                .anyMatch(c -> player.equals(c.worker) && c.status == Contract.Status.ACCEPTED);
    }
}