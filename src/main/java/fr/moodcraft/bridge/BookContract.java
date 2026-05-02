package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookContract {

    public static void give(Player p, Contract c) {

        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle("Contrat Officiel");
        meta.setAuthor("Banque MoodCraft");

        meta.addPage(
                "§l📜 CONTRAT OFFICIEL\n\n" +
                "§7Objet : §e" + c.item +
                "\n§7Quantité : §f" + c.amount +
                "\n§7Paiement : §a" + c.price + "€\n\n" +
                "§8────────────\n" +
                "§7Mission :\n" +
                "§7Fournir les ressources demandées\n\n" +
                "§a✍ Signez pour accepter"
        );

        meta.addPage(
                "§l📦 LIVRAISON\n\n" +
                "§7Une fois signé :\n\n" +
                "§7• Rassemblez les objets\n" +
                "§7• Utilisez §e/contractdeliver\n\n" +
                "§8────────────\n\n" +
                "§a✔ Paiement sécurisé\n" +
                "§a✔ Transaction garantie"
        );

        book.setItemMeta(meta);
        p.getInventory().addItem(book);
    }
}