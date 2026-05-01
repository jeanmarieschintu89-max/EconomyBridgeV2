package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractManager {

    public static Map<UUID, Contract> contracts = new HashMap<>();

    public static class Contract {

        public String from;
        public String to;
        public String item;
        public int amount;
        public double price;

        public boolean accepted = false; // ✔ UNE SEULE FOIS
        public boolean signed = false;   // ✔ signature

        public Contract(String from, String to, String item, int amount, double price) {
            this.from = from;
            this.to = to;
            this.item = item;
            this.amount = amount;
            this.price = price;
        }
    }

    public static UUID create(String from, String to, String item, int amount, double price) {
        UUID id = UUID.randomUUID();
        contracts.put(id, new Contract(from, to, item, amount, price));
        return id;
    }
}