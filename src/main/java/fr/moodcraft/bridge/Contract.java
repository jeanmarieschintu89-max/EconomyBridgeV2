package fr.moodcraft.bridge;

import java.util.UUID;

public class Contract {

    public enum Status {
        CREATED,
        ACCEPTED,
        COMPLETED
    }

    public UUID id = UUID.randomUUID();

    public UUID owner;
    public UUID worker;

    public String item;
    public int amount;
    public double price;

    public Status status = Status.CREATED;

    public Contract(UUID owner, String item, int amount, double price) {
        this.owner = owner;
        this.item = item;
        this.amount = amount;
        this.price = price;
    }
}