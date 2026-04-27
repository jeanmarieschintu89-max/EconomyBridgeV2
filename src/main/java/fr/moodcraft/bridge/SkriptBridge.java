package fr.moodcraft.bridge;

import ch.njol.skript.variables.Variables;

public class SkriptBridge {

    public static void sendBuy(String item, int amount) {

        Object buyObj = Variables.getVariable("qs.buy." + item, null, false);
        Object stockObj = Variables.getVariable("stock." + item, null, false);

        int currentBuy = 0;
        int currentStock = 0;

        if (buyObj instanceof Number) {
            currentBuy = ((Number) buyObj).intValue();
        }

        if (stockObj instanceof Number) {
            currentStock = ((Number) stockObj).intValue();
        }

        currentBuy += amount * 2;
        currentStock -= amount;

        if (currentStock < 0) currentStock = 0;

        Variables.setVariable("qs.buy." + item, currentBuy, null, false);
        Variables.setVariable("stock." + item, currentStock, null, false);
    }
}