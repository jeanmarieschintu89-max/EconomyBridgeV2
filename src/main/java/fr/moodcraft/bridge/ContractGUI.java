package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§6Contrats");

        int slot = 0;

        for (Map.Entry<UUID, ContractManager.Contract> entry : ContractManager.contracts.entrySet()) {

            UUID id = entry.getKey();
            var c = entry.getValue();

            if (!c.to.equalsIgnoreCase(p.getName()) && !c.from.equalsIgnoreCase(p.getName())) continue;
            if (slot >= 9) break;

            int rep = ReputationManager.get(c.from);
            String badge = ReputationManager.getBadge(c.from);

            long remaining = Math.max(0, (c.expireAt - System.currentTimeMillis()) / 1000);
            long minutes = remaining / 60;

            // 📄 INFO
            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.PAPER,
                            "§eContrat #" + id.toString().substring(0, 6),
                            "§7De: §f" + c.from,
                            "§7Objet: §f" + c.item + " x" + c.amount,
                            "§7Paiement: §a" + c.price,
                            "§7Rep: §6" + rep + " " + badge,
                            "§7Expire: §e" + minutes + " min"
                    ));

            // ✔ ACCEPTER
            SafeGUI.safeSet(inv, slot + 9,
                    SafeGUI.item(Material.LIME_DYE, "§aAccepter"));

            // ❌ REFUSER
            SafeGUI.safeSet(inv, slot + 18,
                    SafeGUI.item(Material.RED_DYE, "§cRefuser"));

            // 🗑 ANNULER
            SafeGUI.safeSet(inv, slot + 27,
                    SafeGUI.item(Material.BARRIER, "§4Annuler"));

            slot++;
        }

        // 🔥 BOUTON CREATE (ALIGNÉ AVEC LISTENER)
        SafeGUI.safeSet(inv, 49,
                SafeGUI.item(Material.ANVIL, "§6Créer",
                        "§7Créer un nouveau contrat"));

        p.openInventory(inv);
    }
}