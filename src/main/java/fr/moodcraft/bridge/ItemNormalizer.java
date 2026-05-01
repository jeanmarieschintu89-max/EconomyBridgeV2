package fr.moodcraft.bridge;

public final class ItemNormalizer {

    public static String normalize(String mat) {

        switch (mat) {
            case "gold_ingot": return "gold";
            case "iron_ingot": return "iron";
            case "copper_ingot": return "copper";
            case "lapis_lazuli": return "lapis";
            case "amethyst_shard": return "amethyst";
            case "netherite_ingot": return "netherite";
            case "glowstone_dust": return "glowstone";
        }

        return mat;
    }
}