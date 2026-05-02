package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ContractBuilder {

    public String target;
    public String item;
    public int amount = 1;
    public double price = 100;

    private static final Map<Player, ContractBuilder> map = new HashMap<>();

    public static ContractBuilder get(Player p) {
        return map.computeIfAbsent(p, k -> new ContractBuilder());
    }

    public static void remove(Player p) {
        map.remove(p);
    }
}