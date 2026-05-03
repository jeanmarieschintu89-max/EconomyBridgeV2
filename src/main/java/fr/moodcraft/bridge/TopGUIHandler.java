package fr.moodcraft.bridge;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TopGUIHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        ItemStack item = p.getOpenInventory().getItem(slot);
        if (item == null || !item.hasItemMeta()) return;

        var meta = item.getItemMeta();

        String uuidStr = meta.getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), "target"),
                PersistentDataType.STRING
        );

        if (uuidStr == null) return;

        UUID target = UUID.fromString(uuidStr);

        p.closeInventory();

        // 🔥 ouvre profil
        ProfileGUI.open(p, target);
    }
}