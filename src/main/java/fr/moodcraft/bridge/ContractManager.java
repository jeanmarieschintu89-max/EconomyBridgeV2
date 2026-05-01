package fr.moodcraft.bridge;

import java.util.*;

public class ContractManager {

    public static Map<UUID, Contract> contracts = new HashMap<>();

    public static class Contract {
        public String from;
        public String to;
        public String item;
        public int amount;
        public double price;

        public boolean accepted = false;
        public long expireAt; // ⏳ expiration

        public Contract(String from, String to, String item, int amount, double price) {
            this.from = from;
            this.to = to;
            this.item = item;
            this.amount = amount;
            this.price = price;

            // ⏳ 24h
            this.expireAt = System.currentTimeMillis() + (1000L * 60 * 60 * 24);
        }
    }

    public static UUID create(String from, String to, String item, int amount, double price) {
        UUID id = UUID.randomUUID();
        contracts.put(id, new Contract(from, to, item, amount, price));
        return id;
    }
}