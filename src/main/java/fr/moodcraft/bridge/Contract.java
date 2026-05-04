package fr.moodcraft.bridge;

import java.util.UUID;

public class Contract {

    public int id;
    public UUID owner;
    public UUID acceptor;

    public String item;
    public int amount;
    public double price;

    public Status status;

    public enum Status {
        OPEN,
        IN_PROGRESS,
        COMPLETED
    }

    public Contract(UUID owner, String item, int amount, double price) {
        this.owner = owner;
        this.item = item;
        this.amount = amount;
        this.price = price;
    }
}