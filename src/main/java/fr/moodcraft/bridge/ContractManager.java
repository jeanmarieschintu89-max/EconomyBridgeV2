package fr.moodcraft.bridge;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ContractManager {

    public static Contract create(UUID owner, String item, int amount, double price) {

        Contract c = new Contract(owner, item, amount, price);
        c.id = ContractStorage.nextId();
        c.status = Contract.Status.OPEN;

        ContractStorage.add(c);

        System.out.println("✔ Contrat créé: " + item + " x" + amount + " price=" + price);

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

    public static void remove(int id) {
        ContractStorage.remove(id);
    }
}