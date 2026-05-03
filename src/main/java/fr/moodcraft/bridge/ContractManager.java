package fr.moodcraft.bridge;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContractManager {

    // =========================
    // 🆔 NEXT ID
    // =========================
    public static int nextId() {
        return ContractStorage.getAll().size() + 1;
    }

    // =========================
    // ➕ CREATE
    // =========================
    public static Contract create(UUID owner, String item, int amount, double price) {

        Contract c = new Contract(owner, item, amount, price);
        c.id = nextId();
        c.status = Contract.Status.OPEN;

        ContractStorage.add(c);
        return c;
    }

    // =========================
    // 📊 COUNT ACTIVE
    // =========================
    public static int countActive(UUID uuid) {

        int count = 0;

        for (Contract c : ContractStorage.getAll()) {
            if (uuid.equals(c.owner)
                    && c.status != Contract.Status.COMPLETED) {
                count++;
            }
        }

        return count;
    }

    // =========================
    // 📋 GET ALL
    // =========================
    public static List<Contract> getAll() {
        return ContractStorage.getAll();
    }

    // =========================
    // 📜 GET OPEN
    // =========================
    public static List<Contract> getOpenContracts() {
        return ContractStorage.getAll().stream()
                .filter(c -> c.status == Contract.Status.OPEN)
                .collect(Collectors.toList());
    }

    // =========================
    // 🎯 GET BY WORKER
    // =========================
    public static List<Contract> getByWorker(UUID uuid) {
        return ContractStorage.getAll().stream()
                .filter(c -> uuid.equals(c.acceptor)
                        && c.status == Contract.Status.IN_PROGRESS)
                .collect(Collectors.toList());
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(int id) {
        ContractStorage.remove(id);
    }

    public static Contract get(int id) {
        return ContractStorage.get(id);
    }
}