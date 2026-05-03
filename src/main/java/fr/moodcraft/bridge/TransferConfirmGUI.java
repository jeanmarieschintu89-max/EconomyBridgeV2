package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TransferConfirmGUI {

    public static void open(Player p) {

        TransferBuilder data = TransferBuilder.get(p);

        Inventory inv = Bukkit.createInventory(null, 9, "§6Confirmer");

        SafeGUI.safeSet(inv, 3,
                SafeGUI.item(Material.REDSTONE, "§cAnnuler"));

        SafeGUI.safeSet(inv, 5,
                SafeGUI.item(Material.LIME_DYE, "§aConfirmer",
                        "§7Montant: §f" + data.amount));

        GUIManager.open(p, "transfer_confirm", inv);
    }
}