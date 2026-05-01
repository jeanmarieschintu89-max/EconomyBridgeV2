package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import java.util.UUID;

public class ContractItem {

    public static ItemStack create(UUID id, ContractManager.Contract c) {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        // 📌 titre + auteur
        meta.setTitle("Contrat #" + id.toString().substring(0, 6));
        meta.setAuthor(c.from);

        // 📌 contenu
        meta.addPage(
                "§l📄 CONTRAT\n\n" +
                "§0Créateur: §1" + c.from + "\n" +
                "§0Client: §1" + c.to + "\n\n" +
                "§0Objet:\n§2" + c.item + " x" + c.amount + "\n\n" +
                "§0Paiement:\n§6" + c.price + "€\n\n" +
                "§0Statut:\n" + (c.accepted ? "§aAccepté" : "§cEn attente")
        );

        // 📌 ID caché (IMPORTANT pour suppression)
        meta.setDisplayName("§6Contrat #" + id.toString().substring(0, 6));
        meta.setLore(List.of("§8ID: " + id.toString()));

        book.setItemMeta(meta);
        return book;
    }
}