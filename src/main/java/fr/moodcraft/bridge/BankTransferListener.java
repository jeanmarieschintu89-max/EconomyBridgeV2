@EventHandler
public void click(InventoryClickEvent e) {

    String title = e.getView().getTitle();
    if (title == null) return;

    String clean = title.replaceAll("§.", "");

    // 🔥 IMPORTANT → uniquement le menu principal
    if (!clean.equalsIgnoreCase("Virement")) return;

    // 🔥 ignore si c'est la GUI de confirmation
    if (e.getView().getTopInventory().getSize() != 27) return;

    if (e.getClickedInventory() == null) return;

    if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

    e.setCancelled(true);

    if (!(e.getWhoClicked() instanceof Player p)) return;

    var item = e.getCurrentItem();
    if (item == null || item.getType().isAir()) return;

    int slot = e.getRawSlot();

    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.2f);

    switch (slot) {

        case 11 -> {
            p.closeInventory();

            p.sendMessage("§8────────────");
            p.sendMessage("§eVirement IBAN");
            p.sendMessage("§7Commande:");
            p.sendMessage("§f/ibanpay <iban> <montant>");
            p.sendMessage("§8────────────");
        }

        case 13 -> {
            p.closeInventory();

            ContractBuilder.remove(p);
            TransferBuilder.get(p); // 🔥 remplace create

            TransferTargetGUI.open(p);
        }

        case 15 -> {
            p.closeInventory();

            TransferBuilder.remove(p);

            BankGUI.open(p);
        }
    }
}