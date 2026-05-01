package fr.moodcraft.bridge;

public class MarketEngine {

    public static void sell(String item, double amount) {

        if (!PriceUpdater.ALLOWED.contains(item)) return;

        double price = getPrice(item);

        double impact = (amount * price) / 1000;

        double weight = Main.getInstance().getConfig().getDouble("weight." + item, 1);
        double div = Main.getInstance().getConfig().getDouble("impact." + item, 40);

        impact *= weight;

        if (impact > 2) impact = 2;

        impact *= -1;

        double newPrice = price + (impact / div);

        setPrice(item, newPrice);

        // 📦 stock
        MarketState.stock.put(item,
                MarketState.stock.getOrDefault(item, 0.0) + amount
        );

        PriceUpdater.updateItem(item);
    }

    public static void buy(String item, double amount) {

        if (!PriceUpdater.ALLOWED.contains(item)) return;

        double price = getPrice(item);

        double impact = (amount * price) / 1000;

        double weight = Main.getInstance().getConfig().getDouble("weight." + item, 1);
        double div = Main.getInstance().getConfig().getDouble("impact." + item, 40);

        impact *= weight;

        if (impact > 2) impact = 2;

        double newPrice = price + (impact / div);

        setPrice(item, newPrice);

        PriceUpdater.updateItem(item);
    }

    private static double getPrice(String item) {
        Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
        return (v instanceof Number n) ? n.doubleValue() : 0;
    }

    private static void setPrice(String item, double value) {
        ch.njol.skript.variables.Variables.setVariable("price." + item, value, null, false);
    }
}