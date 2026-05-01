package fr.moodcraft.bridge;

import com.ghostchu.quickshop.api.shop.Shop;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Set;

public final class PriceUpdater {

    public static final Set<String> ALLOWED = Set.of(
            "diamond","iron","gold","emerald","copper",
            "redstone","lapis","coal","quartz",
            "netherite","amethyst","glowstone"
    );

    public static void updateItem(String item) {

        double price = MarketEngine.getPrice(item);

        Set<Shop> shops = ShopIndex.get(item);

        Iterator<Shop> it = shops.iterator();

        new BukkitRunnable() {
            public void run() {

                int count = 0;

                while (it.hasNext() && count < 30) {
                    Shop s = it.next();
                    s.setPrice(price);
                    count++;
                }

                if (!it.hasNext()) cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }

    public static void updateSingle(Shop s, String item) {
        s.setPrice(MarketEngine.getPrice(item));
    }
}