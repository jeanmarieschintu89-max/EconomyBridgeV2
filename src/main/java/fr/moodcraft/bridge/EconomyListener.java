package fr.moodcraft.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyListener implements Listener {

    // ⚠️ On hook via tes propres appels seulement (Vault pur = pas d'event officiel)
    // Donc ici on crée des hooks manuels à appeler dans tes systèmes

    public static void logTransaction(String player, String type, double amount) {
        TransactionLogger.log(player, type, amount);
    }
}