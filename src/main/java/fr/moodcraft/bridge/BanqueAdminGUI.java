public class AdminMarketGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6⚙ Admin Marché");

        SafeGUI.safeSet(inv, 10, SafeGUI.item(Material.COMPARATOR,
                "§eParamètres globaux",
                "§7Modifier le comportement du marché",
                "",
                "§aClique pour ouvrir"));

        SafeGUI.safeSet(inv, 12, SafeGUI.item(Material.CHEST,
                "§bItems",
                "§7Configurer chaque ressource",
                "",
                "§aClique pour ouvrir"));

        SafeGUI.safeSet(inv, 14, SafeGUI.item(Material.NETHER_STAR,
                "§dSimulation",
                "§7Tester le marché",
                "",
                "§aClique pour ouvrir"));

        SafeGUI.safeSet(inv, 16, SafeGUI.item(Material.BARRIER,
                "§cReset / Debug",
                "§7Réinitialiser / tester",
                "",
                "§aClique pour ouvrir"));

        SafeGUI.safeSet(inv, 22, SafeGUI.item(Material.ARROW,
                "§cRetour"));

        p.openInventory(inv);
    }
}