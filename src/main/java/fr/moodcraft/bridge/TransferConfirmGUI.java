package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TransferConfirmGUI {

    public static void open(Player p) {

        var b = TransferBuilder.get(p);

        Inventory inv = Bukkit.createInventory(null, 27, "§eVirement");

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§7Réduire montant"));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.GOLD_INGOT, "§eMontant",
                "§f" + b.amount + "€",
                "",
                "§7Cible:",
                "§a" + b.target));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.EMERALD, "§a+100",
                "§7Augmenter montant"));

        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider"));

        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler"));

        p.openInventory(inv);
    }
}