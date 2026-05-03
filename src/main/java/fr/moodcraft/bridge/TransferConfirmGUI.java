public class TransferConfirmGUI {

    public static void open(Player p) {

        var b = TransferBuilder.get(p);

        Inventory inv = Bukkit.createInventory(null, 27, "§eConfirmation virement");

        // ➖ -100
        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE, "§c-100",
                "§8────────",
                "§7Réduire le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // 💰 CENTRE (INFO SEULEMENT)
        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.GOLD_INGOT, "§eMontant",
                "§8────────",
                "§f" + b.amount + "€",
                "",
                "§7Cible:",
                "§a" + (b.target == null ? "Non défini" : b.target),
                "",
                "§7❗ Utilise les boutons pour modifier"));

        // ➕ +100
        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.EMERALD, "§a+100",
                "§8────────",
                "§7Augmenter le montant",
                "",
                "§7Montant actuel:",
                "§f" + b.amount + "€"));

        // ❌ ANNULER
        SafeGUI.safeSet(inv, 18, SafeGUI.item(Material.BARRIER, "§cAnnuler",
                "§8────────",
                "§7Annuler le virement"));

        // ✅ VALIDER (SEUL bouton qui valide)
        SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.LIME_DYE, "§aValider",
                "§8────────",
                "§7Confirmer le virement",
                "",
                "§7Montant:",
                "§f" + b.amount + "€"));

        p.openInventory(inv);
    }
}