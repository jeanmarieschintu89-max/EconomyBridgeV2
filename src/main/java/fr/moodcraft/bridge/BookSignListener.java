package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

public class BookSignListener implements Listener {

    @EventHandler
    public void onSign(PlayerEditBookEvent e) {

        if (!e.isSigning()) return;

        if (!"Contrat Officiel".equalsIgnoreCase(e.getNewBookMeta().getTitle())) return;

        Contract c = ContractManager.getOpen();
        if (c == null) return;

        c.worker = e.getPlayer().getUniqueId();
        c.status = Contract.Status.ACCEPTED;

        e.getPlayer().sendMessage("§a✔ Contrat accepté !");
    }
}