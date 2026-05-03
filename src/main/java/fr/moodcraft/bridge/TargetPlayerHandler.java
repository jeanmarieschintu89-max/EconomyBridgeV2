package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TargetPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        Player target = Bukkit.getPlayerExact(p.getOpenInventory().getItem(slot).getItemMeta().getDisplayName());

        if (target == null) {
            p.sendMessage("§cJoueur invalide");
            return;
        }

        TransferBuilder.get(p).target = target.getUniqueId();

        TransferConfirmGUI.open(p);
    }
}