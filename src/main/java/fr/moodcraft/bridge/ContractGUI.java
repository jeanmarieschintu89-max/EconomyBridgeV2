int rep = ReputationManager.get(c.from);
String badge = ReputationManager.getBadge(c.from);

inv.setItem(slot, ItemBuilder.of(Material.PAPER,
        "§eContrat #" + id.toString().substring(0, 6),
        "§7De: §f" + c.from,
        "§7Objet: §f" + c.item + " x" + c.amount,
        "§7Paiement: §a" + c.price + "€",
        "",
        "§7Réputation: §6" + rep,
        "§7Statut: " + badge,
        "",
        c.accepted ? "§a✔ Accepté" : "§e⏳ En attente"
));

// ✔ ACCEPTER
inv.setItem(slot + 9, ItemBuilder.of(Material.LIME_DYE, "§a✔ Accepter"));

// ❌ REFUSER
inv.setItem(slot + 18, ItemBuilder.of(Material.RED_DYE, "§c❌ Refuser"));

// ❌ ANNULER (nouveau)
inv.setItem(slot + 27, ItemBuilder.of(Material.BARRIER, "§4❌ Annuler contrat"));