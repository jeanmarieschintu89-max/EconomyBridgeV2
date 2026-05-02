package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractBuilder {

    private static final Map<UUID, ContractBuilder> builders = new HashMap<>();

    public String item;
    public int amount = 1;
    public double price = 100;

    public static ContractBuilder get(UUID uuid) {
        return builders.computeIfAbsent(uuid, k -> new ContractBuilder());
    }

    public static void remove(UUID uuid) {
        builders.remove(uuid);
    }
}