package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

public class BookSignListener implements Listener {

    @EventHandler
    public void onSign(PlayerEditBookEvent e) {

        if (!e.isSigning()) return;

        String title = e.getNewBookMeta().getTitle();

        if (title == null || !title.equalsIgnoreCase("Contrat MoodCraft")) return;

        String[] pages = e.getNewBookMeta().getPages().toArray(new String[0]);

        Player p = e.getPlayer();

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        if (b == null) {
            p.sendMessage("§cErreur contrat.");
            return;
        }

        // 🔧 création finale
        Contract c = new Contract();

        c.id = ContractManager.nextId();
        c.owner = p.getUniqueId();
        c.item = b.item;
        c.amount = b.amount;
        c.price = b.price;
        c.status = "OPEN";

        ContractStorage.add(c);

        ContractBuilder.remove(p.getUniqueId());

        p.sendMessage("§a✔ Contrat validé !");
    }
}