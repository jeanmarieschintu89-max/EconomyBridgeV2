package fr.moodcraft.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GUIManager {

    private static final Map<UUID, String> open = new HashMap<>();

    // =========================
    // 📂 OPEN GUI
    // =========================
    public static void open(Player p, String id, Inventory inv) {

        open.put(p.getUniqueId(), id);

        System.out.println("[DEBUG OPEN] " + p.getName() + " → " + id);

        p.openInventory(inv);
    }

    // =========================
    // 🔍 GET GUI ID
    // =========================
    public static String get(Player p) {
        String id = open.get(p.getUniqueId());

        System.out.println("[DEBUG GET] " + p.getName() + " → " + id);

        return id;
    }

    // =========================
    // ❌ CLOSE GUI
    // =========================
    public static void close(Player p) {

        System.out.println("[DEBUG CLOSE] " + p.getName());

        open.remove(p.getUniqueId());
    }

    // =========================
    // 🎯 HANDLE CLICK
    // =========================
    public static void handle(Player p, int slot) {

        String id = get(p);
        if (id == null) return;

        GUIHandler handler = switch (id) {

            case "main_menu" -> new MainMenuHandler();
            case "bank_main" -> new BankHandler();
            case "transfer_target" -> new TargetPlayerHandler();
            case "transfer_confirm" -> new TransferConfirmHandler();
            case "market_list" -> new MarketItemListHandler();
            case "teleport" -> new TeleportHandler();

            default -> null;
        };

        if (handler != null) {
            handler.onClick(p, slot);
        } else {
            System.out.println("[DEBUG] Aucun handler pour " + id);
        }
    }
}