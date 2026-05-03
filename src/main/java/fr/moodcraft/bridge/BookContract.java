package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookContract {

    public static void give(Player p, ContractBuilder b) {

        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle("Contrat MoodCraft");
        meta.setAuthor(p.getName());

        meta.addPage(
                "§6Contrat\n\n" +
                "Objet: " + b.item + "\n" +
                "Quantité: " + b.amount + "\n" +
                "Prix: " + b.price + "\n\n" +
                "§7Signe pour valider"
        );

        book.setItemMeta(meta);

        p.getInventory().addItem(book);

        p.sendMessage("§e✍️ Signe le livre pour valider ton contrat");
    }
}