package fr.moodcraft.bridge;

import java.util.*;

public class ContractManager {

    public static final Map<Integer, Contract> contracts = new HashMap<>();
    private static int idCounter = 0;

    public static int create(UUID owner, String item, int amount, double price) {

        int id = ++idCounter;

        Contract c = new Contract();
        c.id = id;
        c.owner = owner;
        c.item = item;
        c.amount = amount;
        c.price = price;
        c.status = Contract.Status.OPEN;

        contracts.put(id, c);

        return id;
    }

    public static List<Contract> getOpenContracts() {
        return contracts.values().stream()
                .filter(c -> c.status == Contract.Status.OPEN)
                .toList();
    }

    public static Contract get(int id) {
        return contracts.get(id);
    }

    public static void remove(int id) {
        contracts.remove(id);
    }
}