package fr.moodcraft.bridge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContractManager {

    public static Contract create(UUID owner, String item, int amount, double price) {

        Contract c = new Contract(owner, item, amount, price);
        c.id = ContractStorage.nextId();
        c.status = Contract.Status.OPEN;

        ContractStorage.add(c);
        return c;
    }

    public static Contract get(int id) {
        return ContractStorage.get(id);
    }

    public static List<Contract> getAll() {
        return ContractStorage.getAll();
    }

    public static List<Contract> getOpenContracts() {
        return ContractStorage.getOpen();
    }

    public static List<Contract> getByWorker(UUID uuid) {
        return ContractStorage.getAll().stream()
                .filter(c -> uuid.equals(c.acceptor)
                        && c.status == Contract.Status.IN_PROGRESS)
                .collect(Collectors.toList());
    }

    // =========================
    // 💸 PAIEMENT AUTO
    // =========================
    public static boolean pay(Contract c) {

        if (c.paid) return false;

        String ownerId = c.owner.toString();
        String workerId = c.acceptor.toString();

        double ownerBank = BankStorage.get(ownerId);

        if (ownerBank < c.price) {
            return false; // pas assez d'argent
        }

        // 💸 transfert
        BankStorage.set(ownerId, ownerBank - c.price);
        BankStorage.set(workerId, BankStorage.get(workerId) + c.price);

        c.paid = true;
        c.status = Contract.Status.COMPLETED;

        ContractStorage.update(c);

        // 💬 messages stylés
        Player owner = Bukkit.getPlayer(c.owner);
        Player worker = Bukkit.getPlayer(c.acceptor);

        if (worker != null) {
            worker.sendMessage("");
            worker.sendMessage("§8╔════════════════════════════╗");
            worker.sendMessage("§8║   §a✔ Contrat terminé");
            worker.sendMessage("§8╠════════════════════════════╣");
            worker.sendMessage("§8║ §7Gain: §a+" + (int) c.price + "€");
            worker.sendMessage("§8╚════════════════════════════╝");
        }

        if (owner != null) {
            owner.sendMessage("");
            owner.sendMessage("§8╔════════════════════════════╗");
            owner.sendMessage("§8║   §6📦 Livraison reçue");
            owner.sendMessage("§8╠════════════════════════════╣");
            owner.sendMessage("§8║ §7Paiement: §c-" + (int) c.price + "€");
            owner.sendMessage("§8╚════════════════════════════╝");
        }

        return true;
    }
}