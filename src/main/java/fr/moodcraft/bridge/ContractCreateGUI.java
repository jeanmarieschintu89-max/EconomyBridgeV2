package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContractCreateGUI {

    public static void open(Player p) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

        // 📦 ITEM
        SafeGUI.safeSet(inv, 10, SafeGUI.item(
                b.item != null ? Material.valueOf(b.item.toUpperCase()) : Material.BARRIER,
                "§eObjet demandé",
                "§8────────────",
                "§7Sélection actuelle",
                "",
                "§f" + (b.item == null ? "Aucun objet" : b.item),
                "",
                "§8Dépose un item"));

        // 📊 QUANTITÉ
        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER,
                "§eQuantité",
                "§8────────────",
                "§7Nombre demandé",
                "",
                "§a" + b.amount,
                "",
                "§a+1 clic gauche",
                "§c-1 clic droit",
                "§e+10 shift"));

        // 💰 PRIX
        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.GOLD_INGOT,
                "§ePrix unitaire",
                "§8────────────",
                "§7Prix par objet",
                "",
                "§6" + b.price + "€",
                "",
                "§a+10 clic gauche",
                "§c-10 clic droit",
                "§e+100 shift"));

        // 💸 TOTAL
        double total = b.amount * b.price;

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD,
                "§aValeur totale",
                "§8────────────",
                "§7Montant final",
                "",
                "§a" + total + "€",
                "",
                "§7Résumé du contrat"));

        // ✅ VALIDER
        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE,
                "§aValider contrat",
                "§8────────────",
                "§7Créer la commande",
                "",
                "§aPaiement sécurisé",
                "§7Attribué à un joueur",
                "",
                "§8Clique pour confirmer"));

        // ❌ ANNULER
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
                "§cAnnuler",
                "§8────────────",
                "§7Fermer le menu"));

        p.openInventory(inv);
    }
}