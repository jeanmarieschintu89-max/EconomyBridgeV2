public static void log(String player, String type, double amount) {
    log(player, type, amount, null);
}

public static void log(String player, String type, double amount, String target) {

    String date = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date());

    String line;

    if (target != null) {
        line = date + "||" + type + " -> " + target + "||" + amount;
    } else {
        line = date + "||" + type + "||" + amount;
    }

    logs.computeIfAbsent(player, k -> new java.util.ArrayList<>()).add(line);
    save();
}