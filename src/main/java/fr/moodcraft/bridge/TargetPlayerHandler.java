package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TargetPlayerHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ItemStack item = p.getOpenInventory().getTopInventory().getItem(slot);

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        // 🔥 récup UUID (SAFE)
        String uuidStr = meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), "target"),
                PersistentDataType.STRING
        );

        if (uuidStr == null) {
            p.sendMessage("§cErreur: joueur invalide");
            return;
        }

        UUID uuid = UUID.fromString(uuidStr);
        Player target = Bukkit.getPlayer(uuid);

        if (target == null) {
            p.sendMessage("§cJoueur hors ligne");
            return;
        }

        // 💾 stockage
        TransferBuilder.get(p).target = uuid;

        // 🔥 ouvrir GUI suivant
        TransferAmountGUI.open(p);
    }
}