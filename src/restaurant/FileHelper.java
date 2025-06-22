package restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static void appendToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("❌ Error writing to file " + filename + ": " + e.getMessage());
        }
    }

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading from file " + filename + ": " + e.getMessage());
        }
        return lines;
    }

    public static void overwriteFile(String filename, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("❌ Error overwriting file " + filename + ": " + e.getMessage());
        }
    }
}