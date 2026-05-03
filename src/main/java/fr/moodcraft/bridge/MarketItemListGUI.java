public class MarketItemListGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§bItems Marché");

        int slot = 0;

        for (String item : MarketState.base.keySet()) {

            double price = MarketState.base.get(item);

            SafeGUI.safeSet(inv, slot,
                    SafeGUI.item(Material.DIAMOND,
                            "§b" + item,
                            "§7Prix: §f" + price + "€",
                            "",
                            "§aClique pour modifier"));

            slot++;
        }

        p.openInventory(inv);
    }
}