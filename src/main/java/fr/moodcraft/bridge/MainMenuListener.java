switch (e.getSlot()) {

    // 💰 Comptes
    case 4 -> {
        p.closeInventory();
        BankGUI.open(p);
    }

    // 📈 Marché
    case 10 -> {
        p.closeInventory();
        p.performCommand("prix");
    }

    // 🏦 Banque
    case 11 -> {
        p.closeInventory();
        BankGUI.open(p);
    }

    // 📄 Contrats (🔥 FIX)
    case 12 -> {
        p.closeInventory();
        p.performCommand("contrats");
    }

    // 🏙️ Ville
    case 14 -> {
        p.closeInventory();
        p.performCommand("townmenu");
    }

    // ⚒️ Jobs (🔥 déplacé)
    case 15 -> {
        p.closeInventory();
        p.performCommand("jobs join");
    }

    // 🧭 Téléport
    case 16 -> {
        p.closeInventory();
        TeleportGUI.open(p);
    }

    // ℹ️ Infos
    case 21 -> {
        p.sendMessage("§7💡 Astuce: achète bas, vends haut !");
    }

    // 🔥 Admin
    case 23 -> {
        if (p.hasPermission("econ.admin")) {
            p.closeInventory();
            p.performCommand("banqueadmin");
        } else {
            p.sendMessage("§c❌ Accès refusé.");
        }
    }
}