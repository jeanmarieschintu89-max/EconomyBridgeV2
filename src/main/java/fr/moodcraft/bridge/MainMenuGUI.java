// =========================
// 💰 COMPTES (CENTRE HAUT)
// =========================
int rep = ReputationManager.get(p.getName());
String badge = ReputationManager.getBadge(p.getName());

inv.setItem(4, ItemBuilder.of(Material.CLOCK, "§e💰 Comptes",
        "§7💵 Portefeuille: §a" + money + "€",
        "§7🏦 Banque: §b" + bankMoney + "€",
        "",
        "§7🏙️ Ville: §a" + townName,
        "§7💰 Trésor: §6" + df.format(townBalance).replace(",", " ") + "€",
        "",
        "§7⭐ Réputation: §f" + rep,
        "§7🏅 Statut: " + badge,
        "",
        "§8Clique pour gérer"));