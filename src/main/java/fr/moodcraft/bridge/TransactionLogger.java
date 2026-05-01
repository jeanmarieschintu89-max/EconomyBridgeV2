package fr.moodcraft.bridge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionLogger {

    private static File file;

    public static void init() {
        file = new File(Main.getInstance().getDataFolder(), "transactions.log");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void log(String player, String type, double amount) {

        String line = "[" + LocalDateTime.now() + "] "
                + player + " " + type + " " + amount + "€\n";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}