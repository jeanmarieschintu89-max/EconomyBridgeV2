package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TargetPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        // 🔒 sécurité slot
        if (slot >= p.getOpenInventory().getTopInventory().getSize()) return;

        // 📦 item
        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        if (item == null || item.getItemMeta() == null) return;

        String name = item.getItemMeta().getDisplayName();
        if (name == null) return;

        // 🧼 clean couleur
        String clean = name.replaceAll("§.", "");

        Player target = Bukkit.getPlayerExact(clean);

        if (target == null) {
            p.sendMessage("§cJoueur introuvable");
            return;
        }

        // 💾 stockage cible
        TransferBuilder.get(p).target = target.getUniqueId();

        // 🔥 NOUVEAU FLOW → montant
        TransferAmountGUI.open(p);
    }
}