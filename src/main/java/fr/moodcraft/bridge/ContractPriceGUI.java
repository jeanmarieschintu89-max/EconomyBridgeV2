package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractPriceGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§fPrix");

        // ➖ -100
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                Material.RED_CONCRETE,
                "§c-100",
                "§8────────────",
                "§7Prix actuel:",
                "§6" + b.price + "€"
        ));

        // ➖ -10
        SafeGUI.safeSet(inv, 11, SafeGUI.item(
                Material.RED_STAINED_GLASS,
                "§c-10",
                "§8────────────",
                "§7Prix actuel:",
                "§6" + b.price + "€"
        ));

        // 💰 VALIDER (CENTRE)
        SafeGUI.safeSet(inv, 13, SafeGUI.item(
                Material.GOLD_INGOT,
                "§aValider le prix",
                "§8────────────",
                "§7Prix choisi:",
                "§6" + b.price + "€",
                "",
                "§8Clique pour confirmer"
        ));

        // ➕ +10
        SafeGUI.safeSet(inv, 15, SafeGUI.item(
                Material.GREEN_STAINED_GLASS,
                "§a+10",
                "§8────────────",
                "§7Prix actuel:",
                "§6" + b.price + "€"
        ));

        // ➕ +100
        SafeGUI.safeSet(inv, 16, SafeGUI.item(
                Material.LIME_CONCRETE,
                "§a+100",
                "§8────────────",
                "§7Prix actuel:",
                "§6" + b.price + "€"
        ));

        // 🔙 RETOUR
        SafeGUI.safeSet(inv, 26, SafeGUI.item(
                Material.ARROW,
                "§c⬅ Retour"
        ));

        GUIManager.open(p, "contract_price", inv);
    }
}