package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private static Economy eco;

    public static Economy getEconomy() {

        if (eco == null) {
            RegisteredServiceProvider<Economy> rsp =
                    Bukkit.getServicesManager().getRegistration(Economy.class);

            if (rsp != null) {
                eco = rsp.getProvider();
            }
        }

        return eco;
    }
}