package fr.moodcraft.bridge;

import java.util.*;

public class ContractStorage {

    private static final Map<Integer, Contract> contracts = new HashMap<>();
    private static final Map<Integer, Contract> slotMap = new HashMap<>();

    private static int lastId = 0;

    public static int nextId() {
        return ++lastId;
    }

    public static void add(Contract c) {
        contracts.put(c.id, c);
    }

    public static List<Contract> getAll() {
        return new ArrayList<>(contracts.values());
    }

    public static List<Contract> getOpen() {
        List<Contract> list = new ArrayList<>();

        for (Contract c : contracts.values()) {
            if (c.status == Contract.Status.OPEN) {
                list.add(c);
            }
        }

        return list;
    }

    public static void remove(int id) {
        contracts.remove(id);
    }

    // 🔗 GUI mapping
    public static void setSlot(int slot, Contract c) {
        slotMap.put(slot, c);
    }

    public static Contract getBySlot(int slot) {
        return slotMap.get(slot);
    }

    public static void clearSlots() {
        slotMap.clear();
    }
}