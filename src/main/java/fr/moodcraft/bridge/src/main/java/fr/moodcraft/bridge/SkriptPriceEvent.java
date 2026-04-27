package fr.moodcraft.bridge;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkriptPriceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final String id;
    private double price = 0;

    public SkriptPriceEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
