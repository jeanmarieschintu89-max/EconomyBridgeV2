package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TransferConfirmHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        TransferBuilder data = TransferBuilder.get(p);

        switch (slot) {

            case 3 -> BankGUI.open(p);

            case 5 -> {

                if ("player".equals(data.type)) {

                    Player target = Bukkit.getPlayer(data.target);

                    if (target == null) {
                        p.sendMessage("§cJoueur hors ligne");
                        return;
                    }

                    // 💸 À ADAPTER SELON TON SYSTÈME
                    p.performCommand("bank " + p.getName() + " -" + data.amount);
                    p.performCommand("bank " + target.getName() + " " + data.amount);
                }

                p.sendMessage("§aVirement effectué !");
                TransferBuilder.clear(p);
                p.closeInventory();
            }
        }
    }
}