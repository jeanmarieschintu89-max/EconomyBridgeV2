public static void updateItem(String item) {

    Object v = ch.njol.skript.variables.Variables.getVariable("price." + item, null, false);
    if (!(v instanceof Number n)) return;

    double target = n.doubleValue();

    double base = getDouble("base." + item, target);
    double maxStep = Math.max(2, base * 0.10);
    double clamped = clampStep(item, target, maxStep);

    Set<Shop> shops = ShopIndex.get(item);
    if (shops == null || shops.isEmpty()) return;

    Bukkit.getScheduler().runTask(Main.getInstance(), () -> {

        for (Shop s : shops) {

            if (Math.abs(s.getPrice() - clamped) < 0.1) continue;

            s.setPrice(clamped);
        }

    });

    Main.getInstance().getLogger().info("Sync: " + item + " -> " + clamped + " (" + shops.size() + " shops)");
}