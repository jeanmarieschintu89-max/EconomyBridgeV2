package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class TransferBuilder {

    public String target;
    public double amount = 100;

    private static final Map<Player, TransferBuilder> map = new HashMap<>();

    public static TransferBuilder get(Player p) {
        return map.computeIfAbsent(p, k -> new TransferBuilder());
    }

    public static void remove(Player p) {
        map.remove(p);
    }
}