package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 👤 PROFIL
            case 4 -> openNext(() ->
                    ProfileGUI.open(p, p.getUniqueId())
            );

            // 💰 BANQUE
            case 10 -> openNext(() ->
                    BankGUI.open(p)
            );

            // 📜 CONTRATS
            case 12 -> openNext(() ->
                    ContractGUI.open(p)
            );

            // 💼 BOURSE
            case 14 -> openNext(() ->
                    PriceGUI.open(p)
            );

            // 🧭 TELEPORT
            case 16 -> openNext(() ->
                    TeleportGUI.open(p)
            );

            // 🗺️ VILLE
            case 19 -> openNext(() ->
                    p.performCommand("townmenu")
            );

            // ⛏ MÉTIERS
            case 21 -> openNext(() ->
                    p.performCommand("jobs join")
            );

            // 🏆 CLASSEMENT
            case 23 -> openNext(() ->
                    TopRepGUI.open(p)
            );

            // ❌ FERMER
            case 26 -> p.closeInventory();
        }
    }

    // 🔥 ouverture safe (évite bug inventaire)
    private void openNext(Runnable action) {
        Bukkit.getScheduler().runTask(Main.getInstance(), action);
    }
}