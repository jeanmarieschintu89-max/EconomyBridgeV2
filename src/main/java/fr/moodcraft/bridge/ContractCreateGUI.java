public static void open(Player p) {

    ContractBuilder b = ContractBuilder.get(p.getUniqueId());

    Inventory inv = Bukkit.createInventory(null, 27, "§fCréer contrat");

    // 📦 ITEM
    SafeGUI.safeSet(inv, 10, SafeGUI.item(
            b.item != null ? Material.valueOf(b.item.toUpperCase()) : Material.BARRIER,
            "§eObjet demandé",
            "§8──────────── §7",
            "§7Sélection actuelle §7",
            "",
            "§f" + (b.item == null ? "Aucun objet" : b.item),
            "",
            "§8Dépose un item §7"));

    // 📊 QUANTITÉ
    SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.PAPER,
            "§eQuantité",
            "§8──────────── §7",
            "§7Nombre demandé §7",
            "",
            "§a" + b.amount,
            "",
            "§a+1 clic gauche §7",
            "§c-1 clic droit §7",
            "§e+10 shift §7"));

    // 💰 PRIX
    SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.GOLD_INGOT,
            "§ePrix unitaire",
            "§8──────────── §7",
            "§7Prix par objet §7",
            "",
            "§6" + b.price + "€",
            "",
            "§a+10 clic gauche §7",
            "§c-10 clic droit §7",
            "§e+100 shift §7"));

    // 💸 TOTAL
    double total = b.amount * b.price;

    SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.EMERALD,
            "§aValeur totale",
            "§8──────────── §7",
            "§7Montant final §7",
            "",
            "§a" + total + "€",
            "",
            "§7Résumé du contrat §7"));

    // ✅ VALIDER
    SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.LIME_CONCRETE,
            "§aValider contrat",
            "§8──────────── §7",
            "§7Créer la commande §7",
            "",
            "§aPaiement sécurisé §7",
            "§7Attribué à un joueur §7",
            "",
            "§8Clique pour confirmer §7"));

    // ❌ ANNULER
    SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER,
            "§cAnnuler",
            "§8──────────── §7",
            "§7Fermer le menu §7"));

    p.openInventory(inv);
}