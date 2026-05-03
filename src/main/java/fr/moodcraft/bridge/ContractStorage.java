package fr.moodcraft.bridge;

import java.util.*;

public class ContractStorage {

    // =========================
    // 📦 STOCKAGE PRINCIPAL
    // =========================
    private static final Map<Integer, Contract> contracts = new HashMap<>();

    // =========================
    // 🔗 SLOT → CONTRAT (GUI)
    // =========================
    private static final Map<Integer, Contract> slotMap = new HashMap<>();

    // =========================
    // ➕ AJOUT CONTRAT
    // =========================
    public static void add(Contract contract) {
        contracts.put(contract.id, contract);
    }

    // =========================
    // 📥 GET PAR ID
    // =========================
    public static Contract get(int id) {
        return contracts.get(id);
    }

    // =========================
    // 📋 GET TOUS
    // =========================
    public static List<Contract> getAll() {
        return new ArrayList<>(contracts.values());
    }

    // =========================
    // 📋 GET CONTRATS OUVERTS
    // =========================
    public static List<Contract> getOpen() {

        List<Contract> list = new ArrayList<>();

        for (Contract c : contracts.values()) {

            // ❌ AVANT
            // if ("OPEN".equalsIgnoreCase(c.status))

            // ✅ MAINTENANT
            if (c.status == Contract.Status.OPEN) {
                list.add(c);
            }
        }

        return list;
    }

    // =========================
    // 🔄 UPDATE
    // =========================
    public static void update(Contract contract) {
        contracts.put(contract.id, contract);
    }

    // =========================
    // ❌ REMOVE
    // =========================
    public static void remove(int id) {
        contracts.remove(id);
    }

    // =========================
    // 🔗 SLOT MAPPING
    // =========================
    public static void setSlot(int slot, Contract contract) {
        slotMap.put(slot, contract);
    }

    public static Contract getBySlot(int slot) {
        return slotMap.get(slot);
    }

    public static void clearSlots() {
        slotMap.clear();
    }
}