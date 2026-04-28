@EventHandler
public void onBuy(ShopPurchaseEvent e) {

    String item = e.getItem().getType().name().toLowerCase();
    int amount = e.getAmount();

    // 🔄 NORMALISATION
    switch (item) {
        case "iron_ingot":
        case "raw_iron":
            item = "iron";
            break;

        case "gold_ingot":
        case "raw_gold":
            item = "gold";
            break;

        case "copper_ingot":
        case "raw_copper":
            item = "copper";
            break;

        case "lapis_lazuli":
            item = "lapis";
            break;

        case "redstone":
            item = "redstone";
            break;

        case "diamond":
            item = "diamond";
            break;

        case "emerald":
            item = "emerald";
            break;

        case "coal":
        case "charcoal":
            item = "coal";
            break;

        case "quartz":
            item = "quartz";
            break;

        case "glowstone_dust":
        case "glowstone":
            item = "glowstone";
            break;

        case "amethyst_shard":
            item = "amethyst";
            break;

        case "netherite_ingot":
            item = "netherite";
            break;

        default:
            Bukkit.getLogger().warning("[EcoBridge] Item ignoré: " + item);
            return;
    }

    Bukkit.getLogger().info("[EcoBridge] eco_buy " + item + " " + amount);

    Bukkit.dispatchCommand(
        Bukkit.getConsoleSender(),
        "eco_buy " + item + " " + amount
    );
}