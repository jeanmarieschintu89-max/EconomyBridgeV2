package fr.moodcraft.bridge;

import java.util.UUID;

public class ContractManager {

    public static int countActive(UUID uuid) {
        int count = 0;

        for (Contract c : ContractStorage.getAll()) {
            if (uuid.equals(c.owner) && !"COMPLETED".equalsIgnoreCase(c.status)) {
                count++;
            }
        }

        return count;
    }
}