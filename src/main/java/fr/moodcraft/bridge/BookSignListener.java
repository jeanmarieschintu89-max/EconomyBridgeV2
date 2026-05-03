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

        var p = e.getPlayer();

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) {
            p.sendMessage("§cErreur contrat.");
            return;
        }

        if (b.item == null || b.amount <= 0 || b.price <= 0) {
            p.sendMessage("§cContrat invalide.");
            return;
        }

        // 🚫 limite contrats actifs
        if (ContractManager.countActive(p.getUniqueId()) >= 5) {
            p.sendMessage("§cMax 5 contrats actifs.");
            return;
        }

        // 💰 taxe création (5%)
        double total = b.amount * b.price;
        double fee = total * 0.05;

        if (!BankStorage.remove(p.getUniqueId().toString(), fee)) {
            p.sendMessage("§cFonds insuffisants (taxe création).");
            return;
        }

        Contract c = new Contract();
        c.id = ContractManager.nextId();
        c.owner = p.getUniqueId();
        c.item = b.item;
        c.amount = b.amount;
        c.price = b.price;
        c.status = Contract.Status.OPEN;
        c.paid = false;

        ContractStorage.add(c);
        ContractBuilder.remove(p.getUniqueId());

        p.sendMessage("§a✔ Contrat validé !");
        p.sendMessage("§7Taxe: §c-" + fee + "€");
    }
}