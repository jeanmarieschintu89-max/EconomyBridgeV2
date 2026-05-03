
public class PriceHandler implements GUIHandler {

    @Override
    public void onClick(Player p, int slot) {

        switch (slot) {

            // 🔙 MENU
            case 4:
                MainMenuGUI.open(p);
                return;

            // 💎 MINERAIS
            case 10: sell(p, "netherite"); return;
            case 11: sell(p, "emerald"); return;
            case 12: sell(p, "diamond"); return;

            case 13: sell(p, "gold"); return;
            case 14: sell(p, "copper"); return;
            case 15: sell(p, "iron"); return;

            case 16: sell(p, "glowstone"); return;

            case 19: sell(p, "quartz"); return;
            case 20: sell(p, "amethyst"); return;
            case 21: sell(p, "redstone"); return;
            case 22: sell(p, "lapis"); return;
            case 23: sell(p, "coal"); return;
        }
    }

    private void sell(Player p, String id) {

        double price = MarketEngine.getPrice(id);

        // 👉 ici tu peux connecter à ton système QuickShop / banque
        p.sendMessage("§aVente de §f" + id + " §aau prix de §f" + String.format("%.2f", price) + "€");

        // Exemple simple :
        // Economy.depositPlayer(p, price);

        MarketEngine.recordSell(id, 1);
    }
}