@EventHandler
public void click(InventoryClickEvent e) {

    String title = e.getView().getTitle();
    if (title == null) return;

    String clean = title.replaceAll("§.", "");

    // 🔥 FIX STRICT
    if (!clean.equalsIgnoreCase("Historique")) return;

    if (e.getClickedInventory() == null) return;

    // 🔥 CRITIQUE → protège inventaire joueur
    if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

    e.setCancelled(true);

    if (!(e.getWhoClicked() instanceof Player p)) return;

    int slot = e.getRawSlot();

    String name = p.getName();
    int page = pages.getOrDefault(name, 0);

    if (slot == 22) {
        pages.remove(name);
        p.closeInventory();
        BankGUI.open(p);
        return;
    }

    if (slot == 21 && page > 0) {
        page--;
        pages.put(name, page);
        BankHistoryGUI.open(p, page);
        return;
    }

    if (slot == 23) {
        var logs = TransactionLogger.getAll(name);

        if (logs == null || logs.size() <= (page + 1) * 21) {
            p.sendMessage("§cAucune page suivante");
            return;
        }

        page++;
        pages.put(name, page);
        BankHistoryGUI.open(p, page);
    }
}