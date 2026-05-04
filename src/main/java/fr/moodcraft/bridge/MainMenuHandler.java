package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        switch (slot) {

            // 👤 PROFIL
            case 4 -> openNext(p, () ->
                    ProfileGUI.open(p, p.getUniqueId())
            );

            // 💰 BANQUE
            case 10 -> openNext(p, () ->
                    BankGUI.open(p)
            );

            // 📜 CONTRATS
            case 12 -> openNext(p, () ->
                    ContractGUI.open(p)
            );

            // 💼 BOURSE
            case 14 -> openNext(p, () ->
                    PriceGUI.open(p)
            );

            // 🧭 TELEPORT
            case 16 -> openNext(p, () ->
                    TeleportGUI.open(p)
            );

            // 🗺️ VILLE
            case 19 -> openNext(p, () ->
                    p.performCommand("townmenu")
            );

            // ⛏ MÉTIERS
            case 21 -> openNext(p, () ->
                    p.performCommand("jobs join")
            );

            // 🏆 CLASSEMENT
            case 23 -> openNext(p, () ->
                    TopRepGUI.open(p)
            );

            // ❌ FERMER
            case 26 -> p.closeInventory();
        }
    }

    // 🔥 FIX ICI
    private void openNext(Player p, Runnable action) {

        // 💥 on ferme le GUI actuel
        p.closeInventory();

        // ⏱ on attend 1 tick pour éviter les conflits
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), action, 1L);
    }
}