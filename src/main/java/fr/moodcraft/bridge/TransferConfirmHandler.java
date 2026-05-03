double bank = BankStorage.get(p.getUniqueId().toString());

if (bank < data.amount) {
    p.sendMessage("§cFonds insuffisants");
    return;
}

Player target = Bukkit.getPlayer(data.target);

if (target == null) {
    p.sendMessage("§cJoueur hors ligne");
    return;
}

if (target.equals(p)) {
    p.sendMessage("§cTu ne peux pas te virer à toi-même");
    return;
}

// 💸 TRANSFERT PROPRE
BankStorage.add(p.getUniqueId().toString(), -data.amount);
BankStorage.add(target.getUniqueId().toString(), data.amount);

p.sendMessage("§aVirement envoyé !");
target.sendMessage("§aTu as reçu " + data.amount + "€");

TransferBuilder.clear(p);
p.closeInventory();