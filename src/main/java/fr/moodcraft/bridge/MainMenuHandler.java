package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            case 4 -> {
                p.sendMessage("§7Profil joueur");
            }

            case 10 -> {
                openNext(() -> BankGUI.open(p));
            }

            case 12 -> {
                openNext(() -> ContractGUI.open(p));
            }

            case 14 -> {
                openNext(() -> PriceGUI.open(p));
            }

            case 16 -> {
                openNext(() -> TeleportGUI.open(p));
            }

            case 19 -> {
                openNext(() -> p.performCommand("townmenu"));
            }

            case 21 -> {
                openNext(() -> p.performCommand("jobs join"));
            }

            case 23 -> {
                openNext(() -> TopRepGUI.open(p)); // 👈 TON BUG FIX ICI
            }

            case 26 -> {
                p.closeInventory();
            }
        }
    }

    private void openNext(Runnable action) {
        Bukkit.getScheduler().runTask(Main.getInstance(), action);
    }
}