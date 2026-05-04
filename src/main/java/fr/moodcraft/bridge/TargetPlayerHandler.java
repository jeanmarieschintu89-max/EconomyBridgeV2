package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TargetPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        UUID targetUUID = TargetPlayerGUI.getTarget(slot);

        if (targetUUID == null) return;

        Player target = Bukkit.getPlayer(targetUUID);

        if (target == null) {
            p.sendMessage("§cJoueur introuvable.");
            return;
        }

        // 🔥 feedback
        p.sendMessage("§a✔ Joueur sélectionné: §e" + target.getName());

        // 👉 enregistre la cible (FIX ICI)
        TransferBuilder.setTarget(p, targetUUID);

        // 👉 ouvre le menu suivant (montant)
        TransferAmountGUI.open(p);
    }
}