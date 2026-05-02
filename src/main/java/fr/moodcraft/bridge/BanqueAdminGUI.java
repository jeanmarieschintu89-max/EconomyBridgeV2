inv.setItem(0, ItemBuilder.of(Material.EMERALD_BLOCK, "§a📈 Inflation +5%",
        "§8────────────",
        "§7Augmente tous les prix",
        "§7de la bourse",
        "",
        "§7Effet:",
        "§a• Hausse globale des prix",
        "",
        "§7Utilisation:",
        "§7Relancer une économie lente",
        "§8────────────"));

inv.setItem(1, ItemBuilder.of(Material.REDSTONE_BLOCK, "§c📉 Déflation -5%",
        "§8────────────",
        "§7Réduit tous les prix",
        "",
        "§7Effet:",
        "§c• Baisse globale",
        "",
        "§7Utilisation:",
        "§7Corriger une inflation",
        "§8────────────"));

inv.setItem(2, ItemBuilder.of(Material.CHEST, "§b📦 Config Items",
        "§8────────────",
        "§7Modifier chaque ressource",
        "",
        "§7Impact, rareté, activité",
        "",
        "§e➜ Réglage avancé",
        "§8────────────"));

inv.setItem(3, ItemBuilder.of(Material.BEACON, "§b🔄 Reload Économie",
        "§8────────────",
        "§7Recharge config.yml",
        "",
        "§c⚠ Réinitialise la mémoire",
        "§7temporaire",
        "§8────────────"));

inv.setItem(4, ItemBuilder.of(Material.NETHER_STAR, "§e🔁 Synchroniser",
        "§8────────────",
        "§7Met à jour tous les shops",
        "",
        "§7avec les prix actuels",
        "§8────────────"));

inv.setItem(6, ItemBuilder.of(Material.COMPARATOR, "§d⚙ Config Marché",
        "§8────────────",
        "§7Paramètres globaux",
        "",
        "§7Buy: §a" + buy,
        "§7Sell: §c" + sell,
        "§7Rareté: §e" + rarity,
        "",
        "§7→ Influence les variations",
        "§8────────────"));

inv.setItem(8, ItemBuilder.of(Material.BARRIER, "§4💣 Reset Économie",
        "§8────────────",
        "§7Remet tous les prix",
        "§7à leur base",
        "",
        "§c⚠ Efface le marché",
        "§8────────────"));