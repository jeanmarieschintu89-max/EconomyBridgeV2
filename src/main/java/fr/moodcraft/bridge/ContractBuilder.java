package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractBuilder {

    public String target;
    public String item;
    public ItemStack itemStack;

    public int amount = 1;
    public double price = 100;

    // 🔥 stockage par UUID (safe)
    private static final Map<UUID, ContractBuilder> map = new HashMap<>();

    // =========================
    // 📥 GET (SANS CREATE AUTO)
    // =========================
    public static ContractBuilder get(Player p) {
        return map.get(p.getUniqueId());
    }

    // =========================
    // ➕ CREATE (EXPLICITE)
    // =========================
    public static ContractBuilder create(Player p) {
        ContractBuilder b = new ContractBuilder();
        map.put(p.getUniqueId(), b);
        return b;
    }

    // =========================
    // 🔍 HAS (TRÈS IMPORTANT)
    // =========================
    public static boolean has(Player p) {
        return map.containsKey(p.getUniqueId());
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(Player p) {
        map.remove(p.getUniqueId());
    }

    // =========================
    // 🔄 RESET (OPTIONNEL)
    // =========================
    public static void reset(Player p) {
        ContractBuilder b = map.get(p.getUniqueId());
        if (b != null) {
            b.target = null;
            b.item = null;
            b.itemStack = null;
            b.amount = 1;
            b.price = 100;
        }
    }
}