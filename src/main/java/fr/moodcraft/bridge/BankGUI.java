Inventory inv = Bukkit.createInventory(null, 27, "§b🏦 Banque");

for (int i = 0; i < 27; i++) {
    SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
}

// IBAN
SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.PAPER, "§e📄 IBAN"));

// DEPOT
SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.LIME_DYE, "§a⬆ Déposer"));

// INFO
SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.CLOCK, "§6💰 Comptes",
        "§7Liquide: §a" + SafeGUI.money(cash),
        "§7Banque: §b" + SafeGUI.money(bank)));

// RETRAIT
SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE, "§c⬇ Retirer"));

// VIREMENT
SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.FEATHER, "§e🔁 Virement"));

// HISTORIQUE
SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BOOK, "§d📜 Historique"));

// RETOUR
SafeGUI.safeSet(inv, 26, SafeGUI.item(Material.BARRIER, "§cRetour"));

GUIManager.open(p, "bank_main", inv);