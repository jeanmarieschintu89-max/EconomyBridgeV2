package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ContractPlayerListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        if (title == null) return;

        String clean = title.replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Mes contrats")) return;

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getClickedInventory() == null) return;
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

        e.setCancelled(true);

        int slot = e.getRawSlot();

        if (slot == 49) {
            p.closeInventory();
            ContractGUI.open(p);
            return;
        }

        Contract c = ContractManager.getAll().stream()
                .filter(contract -> p.getUniqueId().equals(contract.worker))
                .skip(slot)
                .findFirst()
                .orElse(null);

        if (c == null) return;

        Material mat;

        try {
            mat = Material.valueOf(c.item);
        } catch (Exception ex) {
            p.sendMessage("§cItem invalide");
            return;
        }

        if (!p.getInventory().contains(mat, c.amount)) {
            p.sendMessage("§cTu n'as pas les items");
            return;
        }

        p.getInventory().removeItem(new ItemStack(mat, c.amount));

        // paiement banque
        String id = p.getUniqueId().toString();
        BankStorage.set(id, BankStorage.get(id) + c.price);

        ReputationManager.add(p.getName(), 1);

        c.status = Contract.Status.COMPLETED;
        ContractManager.remove(c.id);

        p.sendMessage("§a✔ Contrat terminé !");
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);

        p.closeInventory();
    }
}