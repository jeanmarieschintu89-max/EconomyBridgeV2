package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileGUI {

    public static void open(org.bukkit.entity.Player viewer, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Profil");

        String name = Bukkit.getOfflinePlayer(targetUUID).getName();
        if (name == null) name = "Inconnu";

        double bank = BankStorage.get(targetUUID.toString());
        int rep = ReputationManager.get(targetUUID.toString());

        // =========================
        // 🛠️ MÉTIERS
        // =========================
        List<String> jobsLore = new ArrayList<>();

        var targetPlayer = Bukkit.getPlayer(targetUUID);

        if (targetPlayer != null) {
            jobsLore.addAll(JobsHook.getJobsLore(targetPlayer));
        } else {
            jobsLore.add("§7Joueur hors ligne");
        }

        if (jobsLore.isEmpty()) {
            jobsLore.add("§7Aucun métier");
        }

        // =========================
        // 👤 PROFIL
        // =========================
        List<String> lore = new ArrayList<>();

        lore.add("§8────────────");
        lore.add("§7Banque: §6" + (int) bank + "€");
        lore.add("");

        lore.add("§7Réputation: §a" + rep);
        lore.add("§7Statut: " + ReputationManager.getRank(rep));
        lore.add("");

        lore.add("§7Métiers:");
        lore.addAll(jobsLore);

        // 🔥 tête avec skin
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (head.getItemMeta() instanceof SkullMeta meta) {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(targetUUID));
            meta.setDisplayName("§e" + name);
            meta.setLore(lore);
            head.setItemMeta(meta);
        }

        inv.setItem(13, head);

        // =========================
        // 🔥 BORDURES
        // =========================
        SafeGUI.fillBorders(inv, Material.BLACK_STAINED_GLASS_PANE);

        // =========================
        // 🔙 RETOUR
        // =========================
        SafeGUI.safeSet(inv, 26,
                SafeGUI.item(Material.BARRIER, "§cRetour")
        );

        // 🔥 IMPORTANT (sinon pas de handler)
        GUIManager.open(viewer, "profile_gui", inv);
    }
}