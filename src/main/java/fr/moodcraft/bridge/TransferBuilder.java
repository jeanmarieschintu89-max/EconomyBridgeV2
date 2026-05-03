public class TransferBuilder {

    public UUID target;
    public double amount = 100;

    private static final Map<UUID, TransferBuilder> map = new HashMap<>();

    public static TransferBuilder get(Player p) {
        return map.computeIfAbsent(p.getUniqueId(), k -> new TransferBuilder());
    }

    public static void remove(Player p) {
        map.remove(p.getUniqueId());
    }

    public static boolean has(Player p) {
        return map.containsKey(p.getUniqueId());
    }

    public boolean isValid() {
        return target != null && amount > 0;
    }
}