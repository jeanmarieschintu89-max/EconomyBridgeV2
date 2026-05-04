package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    // =========================
    // 💰 GET BALANCE
    // =========================
    public static double getBalance(Player p) {
        Economy e = getEconomy();
        if (e == null) return 0;
        return e.getBalance(p);
    }

    // =========================
    // ➕ ADD MONEY
    // =========================
    public static void add(Player p, double amount) {
        Economy e = getEconomy();
        if (e != null) e.depositPlayer(p, amount);
    }

    // =========================
    // ➖ REMOVE MONEY
    // =========================
    public static void remove(Player p, double amount) {
        Economy e = getEconomy();
        if (e != null) e.withdrawPlayer(p, amount);
    }
}