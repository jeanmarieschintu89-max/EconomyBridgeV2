Economy eco = VaultHook.getEconomy();

double cash = eco != null ? eco.getBalance(p) : 0;
double bank = BankStorage.get(p.getUniqueId().toString());

SafeGUI.safeSet(inv, 11, SafeGUI.item(Material.LIME_DYE, "§a⬆ Déposer",
        "§8────────────",
        "§7Transférer ton argent",
        "§7vers la banque",
        "",
        "§aLiquide: §f" + SafeGUI.money(cash),
        "§bBanque: §f" + SafeGUI.money(bank),
        "",
        "§eClique pour déposer"));

SafeGUI.safeSet(inv, 15, SafeGUI.item(Material.REDSTONE, "§c⬇ Retirer",
        "§8────────────",
        "§7Retirer depuis la banque",
        "",
        "§aLiquide: §f" + SafeGUI.money(cash),
        "§bBanque: §f" + SafeGUI.money(bank),
        "",
        "§eClique pour retirer"));