public class AdminMarketListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {

        String clean = e.getView().getTitle().replaceAll("§.", "");
        if (!clean.equalsIgnoreCase("Admin Marché")) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) return;

        int slot = e.getRawSlot();

        switch (slot) {

            case 10 -> AdminGlobalGUI.open(p);
            case 12 -> AdminItemListGUI.open(p);
            case 14 -> p.sendMessage("§7Simulation bientôt...");
            case 16 -> p.performCommand("ecoreset");
            case 22 -> p.closeInventory();
        }
    }
}