package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6📄 Contrats");

        int slot = 0;

        for (Map.Entry<UUID, ContractManager.Contract> entry : ContractManager.contracts.entrySet()) {

            var c = entry.getValue();

            if (!c.to.equalsIgnoreCase(p.getName())) continue;

            if (slot >= 21) break;

            int rep = ReputationManager.get(c.from);

            inv.setItem(slot, ItemBuilder.of(Material.PAPER,
                    "§eContrat de " + c.from,
                    "§7Objet: §f" + c.item + " x" + c.amount,
                    "§7Paiement: §a" + c.price + "€",
                    "",
                    "§7Réputation: §6" + rep,
                    "",
                    c.accepted ? "§a✔ Accepté" : "§e⏳ En attente",
                    "",
                    "§aClick gauche = accepter",
                    "§cClick droit = refuser"));

            slot++;
        }

        // =========================
        // ➕ CREER CONTRAT
        // =========================
        inv.setItem(22, ItemBuilder.of(Material.EMERALD_BLOCK, "§a➕ Créer un contrat",
                "§7Créer un accord avec un joueur",
                "",
                "§7Commande:",
                "§e/contrat create <joueur> <item> <quantité> <prix>",
                "",
                "§8Clique pour voir"));

        // 🧱 déco
        inv.setItem(18, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));
        inv.setItem(26, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE, " "));

        p.openInventory(inv);
    }
}