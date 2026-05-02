package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractBuilder {

    public String target;
    public String item;

    public ItemStack itemStack; // 🔥 AJOUT IMPORTANT

    public int amount = 1;
    public double price = 100;

    // 🔥 UUID au lieu de Player (évite memory leak)
    private static final Map<UUID, ContractBuilder> map = new HashMap<>();

    public static ContractBuilder get(Player p) {
        return map.computeIfAbsent(p.getUniqueId(), k -> new ContractBuilder());
    }

    public static void remove(Player p) {
        map.remove(p.getUniqueId());
    }
}