package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ProfileGUI {

    public static void open(org.bukkit.entity.Player viewer, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 27, "Profil");

        String name = Bukkit.getOfflinePlayer(targetUUID).getName();

        double bank = BankStorage.get(targetUUID.toString());
        int rep = ReputationManager.get(targetUUID.toString());

        SafeGUI.safeSet(inv, 13, SafeGUI.item(
                Material.PLAYER_HEAD,
                "§e" + name,
                "§8────────────",
                "§7Banque: §6" + (int) bank + "€",
                "",
                "§7Réputation: §a" + rep,
                "§7Statut: " + ReputationManager.getRank(rep)
        ));

        viewer.openInventory(inv);
    }
}