package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

public class IbanGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, "§bVirement IBAN");

        SafeGUI.safeSet(inv, 4,
                SafeGUI.item(Material.PAPER, "§eEntre l'IBAN dans le chat"));

        SafeGUI.safeSet(inv, 8,
                SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, "iban_gui", inv);

        // 🔥 ACTIVE INPUT (clé pour bypass l'auth)
        p.setMetadata("input_active", new FixedMetadataValue(Main.getInstance(), true));

        InputManager.wait(p, "iban_input");
    }
}