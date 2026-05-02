package fr.moodcraft.bridge;

import java.util.*;

public class ContractManager {

    public static final Map<UUID, Contract> contracts = new HashMap<>();

    public static Contract create(UUID owner, String item, int amount, double price) {
        Contract c = new Contract(owner, item, amount, price);
        contracts.put(c.id, c);
        return c;
    }

    public static Contract getOpen() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.CREATED)
                .findFirst().orElse(null);
    }

    public static Contract getByWorker(UUID worker) {
        return contracts.values().stream()
                .filter(c -> worker.equals(c.worker))
                .findFirst().orElse(null);
    }

    public static void remove(UUID id) {
        contracts.remove(id);
    }
}