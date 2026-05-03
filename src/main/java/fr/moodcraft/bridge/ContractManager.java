package fr.moodcraft.bridge;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContractManager {

    // =========================
    // 📊 COMPTE CONTRATS ACTIFS
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
    // 📜 GET TOUS
    // =========================
    public static List<Contract> getAll() {
        return ContractStorage.getAll();
    }

    // =========================
    // 📜 GET OUVERTS
    // =========================
    public static List<Contract> getOpenContracts() {

        return ContractStorage.getAll().stream()
                .filter(c -> c.status == Contract.Status.OPEN)
                .collect(Collectors.toList());
    }

    // =========================
    // 🔍 GET PAR ID
    // =========================
    public static Contract get(int id) {
        return ContractStorage.get(id);
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(int id) {
        ContractStorage.remove(id);
    }

    // =========================
    // 🎯 GET PAR WORKER
    // =========================
    public static List<Contract> getByWorker(UUID uuid) {

        return ContractStorage.getAll().stream()
                .filter(c -> uuid.equals(c.acceptor)
                        && c.status == Contract.Status.IN_PROGRESS)
                .collect(Collectors.toList());
    }
}