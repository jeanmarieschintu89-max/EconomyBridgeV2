package fr.moodcraft.bridge;

import ch.njol.skript.variables.Variables;

public class SkriptBridge {

    public static void sendBuy(String item, int amount) {

        Variables.setVariable("qs.buy." + item, 
            Variables.getVariable("qs.buy." + item, null, false) == null ? amount * 2 :
            (int) Variables.getVariable("qs.buy." + item, null, false) + (amount * 2),
        null, false);

        Variables.setVariable("stock." + item,
            Math.max(0,
                ((Number) Variables.getVariable("stock." + item, null, false)).intValue() - amount
            ),
        null, false);
    }
}