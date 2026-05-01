package fr.moodcraft.bridge;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class ItemNormalizer {

    public static String normalize(Material mat) {
        return switch (mat) {
            case DIAMOND -> "diamond";
            case EMERALD -> "emerald";
            case GOLD_INGOT -> "gold";
            case IRON_INGOT -> "iron";
            case COPPER_INGOT -> "copper";
            case REDSTONE -> "redstone";
            case LAPIS_LAZULI -> "lapis";
            case COAL, CHARCOAL -> "coal";
            case QUARTZ -> "quartz";
            case NETHERITE_INGOT -> "netherite";
            case AMETHYST_SHARD -> "amethyst";
            case GLOWSTONE_DUST -> "glowstone";
            default -> null;
        };
    }

    public static String normalizeBlock(Block block) {
        return switch (block.getType()) {
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> "diamond";
            case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> "emerald";
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> "gold";
            case IRON_ORE, DEEPSLATE_IRON_ORE -> "iron";
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> "copper";
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> "redstone";
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> "lapis";
            case COAL_ORE, DEEPSLATE_COAL_ORE -> "coal";
            case NETHER_QUARTZ_ORE -> "quartz";
            case GLOWSTONE -> "glowstone";
            case AMETHYST_CLUSTER -> "amethyst";
            default -> null;
        };
    }
}