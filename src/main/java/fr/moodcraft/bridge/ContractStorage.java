private static final Map<Integer, Contract> slotMap = new HashMap<>();

public static void setSlot(int slot, Contract contract) {
    slotMap.put(slot, contract);
}

public static Contract getBySlot(int slot) {
    return slotMap.get(slot);
}