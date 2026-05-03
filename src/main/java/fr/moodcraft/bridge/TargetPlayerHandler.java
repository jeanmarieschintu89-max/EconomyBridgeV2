package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TargetPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔒 Sécurité slot
        if (slot >= p.getOpenInventory().getTopInventory().getSize()) return;

        // 📦 Récupération item correcte
        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        if (item == null || item.getItemMeta() == null) return;

        String name = item.getItemMeta().getDisplayName();
        if (name == null) return;

        // 🧼 Nettoyage couleurs Minecraft
        String clean = name.replaceAll("§.", "");

        Player target = Bukkit.getPlayerExact(clean);

        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // 💾 Stockage
        TransferBuilder.get(p).target = target.getUniqueId();

        // 🚀 Ouverture confirm
        TransferConfirmGUI.open(p);
    }
}