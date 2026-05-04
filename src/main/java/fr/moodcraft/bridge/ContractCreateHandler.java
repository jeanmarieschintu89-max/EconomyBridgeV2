package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ContractCreateHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());

        switch (slot) {

            // 📦 ITEM (MAIN HAND)
            case 10 -> {

                ItemStack hand = p.getInventory().getItemInMainHand();

                if (hand == null || hand.getType().isAir()) {
                    p.sendMessage("§c❌ Tiens un objet dans ta main !");
                    return;
                }

                b.itemStack = hand.clone();
                b.item = hand.getType().name();

                p.sendMessage("§a✔ Objet sélectionné: §f" + b.item);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);

                ContractCreateGUI.open(p);
            }

            case 12 -> ContractAmountGUI.open(p);
            case 14 -> ContractPriceGUI.open(p);

            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                ContractConfirmGUI.open(p);
                p.closeInventory();
            }

            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }
        }
    }
}