package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.entity.Player;

public class BookSignListener implements Listener {

    @EventHandler
    public void onSign(PlayerEditBookEvent e) {

        if (!e.isSigning()) return;

        if (!"Contrat Officiel".equalsIgnoreCase(e.getNewBookMeta().getTitle())) return;

        Player worker = e.getPlayer();

        Contract c = ContractManager.getOpen();

        if (c == null) {
            worker.sendMessage("§c❌ Aucun contrat disponible");
            return;
        }

        // 🔒 sécurité : déjà pris
        if (c.status != Contract.Status.CREATED) {
            worker.sendMessage("§c❌ Ce contrat est déjà pris");
            return;
        }

        // 🔥 attribution
        c.worker = worker.getUniqueId();
        c.status = Contract.Status.ACCEPTED;

        worker.sendMessage("§8────────────");
        worker.sendMessage("§a✔ Contrat accepté !");
        worker.sendMessage("§7Utilise §e/contractdeliver §7pour livrer");
        worker.sendMessage("§8────────────");

        // 📩 notifier le client
        Player owner = Bukkit.getPlayer(c.owner);

        if (owner != null && owner.isOnline()) {
            owner.sendMessage("§8────────────");
            owner.sendMessage("§e📜 Ton contrat a été accepté !");
            owner.sendMessage("§7Joueur: §f" + worker.getName());
            owner.sendMessage("§8────────────");
        }
    }
}