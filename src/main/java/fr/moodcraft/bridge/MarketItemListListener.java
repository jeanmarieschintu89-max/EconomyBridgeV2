@EventHandler
public void click(InventoryClickEvent e) {

    String title = e.getView().getTitle();
    if (title == null || !title.contains("Items Marché")) return;

    if (!(e.getWhoClicked() instanceof Player p)) return;

    e.setCancelled(true);

    var item = e.getCurrentItem();
    if (item == null || !item.hasItemMeta()) return;

    String name = item.getItemMeta().getDisplayName().replace("§b", "");

    MarketItemGUI.open(p, name.toLowerCase());
}