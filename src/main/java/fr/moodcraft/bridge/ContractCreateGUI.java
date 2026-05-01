package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractCreateGUI {

    public static Map<UUID, ContractBuilder> builders = new HashMap<>();

    public static void open(Player p) {

        builders.putIfAbsent(p.getUniqueId(), new ContractBuilder());
        var b = builders.get(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§6✏ Création contrat");

        inv.setItem(10, ItemBuilder.of(Material.PLAYER_HEAD, "§e👤 Joueur",
                "§7Actuel: §f" + (b.target == null ? "Aucun" : b.target),
                "§8Clique pour sélectionner"));

        inv.setItem(11, ItemBuilder.of(Material.CHEST, "§e📦 Item",
                "§7Main: §f" + (b.item == null ? "Aucun" : b.item),
                "§8Clique avec item en main"));

        inv.setItem(12, ItemBuilder.of(Material.ANVIL, "§e🔢 Quantité",
                "§7Actuel: §f" + b.amount,
                "§8Gauche +1 / Droite -1"));

        inv.setItem(13, ItemBuilder.of(Material.GOLD_INGOT, "§e💰 Prix",
                "§7Actuel: §f" + b.price + "€",
                "§8Gauche +100 / Droite -100"));

        inv.setItem(16, ItemBuilder.of(Material.EMERALD_BLOCK, "§a✔ Valider",
                "§8Créer le contrat"));

        p.openInventory(inv);
    }
}