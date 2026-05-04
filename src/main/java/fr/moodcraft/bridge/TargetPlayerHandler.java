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

        // 🔥 IMPORTANT : définir l'action
        TransferBuilder.setAction(p, TransferBuilder.Action.PLAYER_TRANSFER);

        // 🔥 enregistrer la cible
        TransferBuilder.setTarget(p, targetUUID);

        // 👉 menu montant
        TransferAmountGUI.open(p);
    }
}