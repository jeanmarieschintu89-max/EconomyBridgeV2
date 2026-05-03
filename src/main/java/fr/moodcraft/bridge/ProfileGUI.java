package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileGUI {

    public static void open(org.bukkit.entity.Player viewer, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 27, "Profil");

        String name = Bukkit.getOfflinePlayer(targetUUID).getName();

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

        SafeGUI.safeSet(inv, 13, SafeGUI.item(
                Material.PLAYER_HEAD,
                "§e" + name,
                lore.toArray(new String[0])
        ));

        viewer.openInventory(inv);
    }
}