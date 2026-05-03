package fr.moodcraft.bridge;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MainMenuHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

        switch (slot) {

            case 10 -> { p.closeInventory(); BankGUI.open(p); }
            case 12 -> { p.closeInventory(); ContractGUI.open(p); }
            case 14 -> { p.closeInventory(); PriceGUI.open(p); }
            case 16 -> { p.closeInventory(); TeleportGUI.open(p); }
            case 19 -> { p.closeInventory(); p.performCommand("townmenu"); }
            case 21 -> { p.closeInventory(); p.performCommand("jobs join"); }
            case 26 -> p.closeInventory();
        }
    }
}