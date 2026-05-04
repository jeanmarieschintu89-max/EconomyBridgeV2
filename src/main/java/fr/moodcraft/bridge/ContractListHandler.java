package fr.moodcraft.bridge;

import org.bukkit.entity.Player;

public class ContractListHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        Contract c = ContractStorage.getBySlot(slot);
        if (c == null) return;

        if (c.owner.equals(p.getUniqueId())) {
            p.sendMessage("§cTu ne peux pas prendre ton propre contrat.");
            return;
        }

        c.acceptor = p.getUniqueId();
        c.status = Contract.Status.IN_PROGRESS;

        p.sendMessage("§a✔ Contrat accepté !");
    }
}