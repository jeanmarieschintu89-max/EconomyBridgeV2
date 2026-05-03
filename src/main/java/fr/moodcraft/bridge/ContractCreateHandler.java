package fr.moodcraft.bridge;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ContractCreateHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ContractBuilder b = ContractBuilder.get(p.getUniqueId());
        if (b == null) return;

        switch (slot) {

            // 📦 SLOT ITEM (FIX)
            case 10 -> {

                ItemStack cursor = p.getItemOnCursor();

                if (cursor == null || cursor.getType().isAir()) {
                    p.sendMessage("§c❌ Mets un objet avec ta souris");
                    return;
                }

                // 🔒 sauvegarde (on copie juste le type)
                b.item = cursor.getType().name();

                // 🔒 IMPORTANT : on ne consomme PAS l'item
                p.setItemOnCursor(cursor);

                p.sendMessage("§a✔ Objet sélectionné: §f" + b.item);

                // 🔄 refresh GUI
                ContractCreateGUI.open(p);
            }

            // 📊 QUANTITÉ
            case 12 -> ContractAmountGUI.open(p);

            // 💰 PRIX
            case 14 -> ContractPriceGUI.open(p);

            // ✍️ VALIDATION
            case 22 -> {

                if (b.item == null || b.amount <= 0 || b.price <= 0) {
                    p.sendMessage("§c❌ Contrat invalide");
                    return;
                }

                // 👉 GUI confirmation (plus de livre)
                ContractConfirmGUI.open(p);

                p.sendMessage("§e✔ Confirme ton contrat");

                p.closeInventory();
            }

            // ❌ ANNULER
            case 26 -> {
                ContractBuilder.remove(p.getUniqueId());
                p.closeInventory();
            }
        }
    }
}