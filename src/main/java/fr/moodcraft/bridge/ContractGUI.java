package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§6📄 Contrats");

        int slot = 0;

        for (Map.Entry<UUID, ContractManager.Contract> entry : ContractManager.contracts.entrySet()) {

            UUID id = entry.getKey();
            var c = entry.getValue();

            if (!c.to.equalsIgnoreCase(p.getName())) continue;

            if (slot >= 45) break;

            int rep = ReputationManager.get(c.from);

            // 📄 CONTRAT
            inv.setItem(slot, ItemBuilder.of(Material.PAPER,
                    "§eContrat #" + id.toString().substring(0, 6),
                    "§7De: §f" + c.from,
                    "§7Objet: §f" + c.item + " x" + c.amount,
                    "§7Paiement: §a" + c.price + "€",
                    "",
                    "§7Réputation: §6" + rep));

            // ✔ ACCEPTER
            inv.setItem(slot + 9, ItemBuilder.of(Material.LIME_DYE,
                    "§a✔ Accepter"));

            // ❌ REFUSER
            inv.setItem(slot + 18, ItemBuilder.of(Material.RED_DYE,
                    "§c❌ Refuser"));

            slot++;
        }

        // ➕ bouton création
        inv.setItem(49, ItemBuilder.of(Material.ANVIL, "§6➕ Créer un contrat"));

        p.openInventory(inv);
    }
}