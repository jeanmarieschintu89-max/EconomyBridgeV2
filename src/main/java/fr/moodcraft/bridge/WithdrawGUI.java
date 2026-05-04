package fr.moodcraft.bridge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WithdrawGUI implements GUIHandler {

    public void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§cRetrait");

        Economy eco = VaultHook.getEconomy();
        double cash = eco != null ? eco.getBalance(p) : 0;
        double bank = BankStorage.get(p.getUniqueId().toString());

        for (int i = 0; i < 27; i++) {
            SafeGUI.safeSet(inv, i, SafeGUI.item(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.REDSTONE,
                "§c-100€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 100 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 13, SafeGUI.item(Material.REDSTONE,
                "§c-1000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 1000 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE,
                "§c-10000€",
                "§8────────────",
                "§7Liquide: §f" + SafeGUI.money(cash),
                "§7Banque: §f" + SafeGUI.money(bank),
                "",
                (bank >= 10000 ? "§aDisponible" : "§cSolde insuffisant")));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.BARRIER, "§cRetour"));

        GUIManager.open(p, this, inv); // 🔥 LIAISON HANDLER
    }

    @Override
    public void onClick(Player p, int slot) {

        Economy eco = VaultHook.getEconomy();
        if (eco == null) return;

        double bank = BankStorage.get(p.getUniqueId().toString());
        double amount = 0;

        if (slot == 11) amount = 100;
        if (slot == 13) amount = 1000;
        if (slot == 15) amount = 10000;

        if (slot == 22) {
            BankGUI.open(p);
            return;
        }

        if (amount == 0) return;

        if (bank < amount) {
            p.sendMessage("§cPas assez d'argent en banque.");
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        // 💰 TRANSFERT
        BankStorage.remove(p.getUniqueId().toString(), amount);
        eco.depositPlayer(p, amount);

        p.sendMessage("§aRetrait de §f" + SafeGUI.money(amount) + " §aeffectué !");
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        // 🔄 refresh GUI
        open(p);
    }
}