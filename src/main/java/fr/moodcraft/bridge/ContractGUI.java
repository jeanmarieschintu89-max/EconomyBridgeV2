package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ContractGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§eContrats"); // 🔥 FIX titre

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
                            "§8────────────",
                            "§7De: §f" + c.from,
                            "§7Objet: §f" + c.item + " x" + c.amount,
                            "§7Paiement: §a" + c.price + "€",
                            "§7Réputation: §f" + rep + " " + badge, // 🔥 FIX §6 → §f
                            "§7Expire dans: §e" + minutes + " min"
                    ));

            // ✔ ACCEPTER
            SafeGUI.safeSet(inv, slot + 9,
                    SafeGUI.item(Material.LIME_DYE, "§aAccepter",
                            "§8────────────",
                            "§7Valider ce contrat"));

            // ❌ REFUSER
            SafeGUI.safeSet(inv, slot + 18,
                    SafeGUI.item(Material.RED_DYE, "§cRefuser",
                            "§8────────────",
                            "§7Refuser la proposition"));

            // 🗑 ANNULER
            SafeGUI.safeSet(inv, slot + 27,
                    SafeGUI.item(Material.BARRIER, "§cAnnuler",
                            "§8────────────",
                            "§7Supprimer ce contrat"));

            slot++;
        }

        // 🔥 BOUTON CREATE (REFONTE PREMIUM)
        SafeGUI.safeSet(inv, 49,
                SafeGUI.item(Material.WRITABLE_BOOK, "§eCréer un contrat",
                        "§8────────────",
                        "§7Proposer un échange",
                        "§7à un joueur",
                        "",
                        "§aConfigurer les paramètres",
                        "§7Objet • Prix • Cible",
                        "",
                        "§8Clique pour créer"));

        p.openInventory(inv);
    }
}