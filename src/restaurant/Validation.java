package restaurant;

import java.util.List;

public class Validation {

    public static boolean isAlpha(String name) {
        return name.matches("[\\p{L} ]+");
    }

    public static boolean isIdUnique(String fileName, String id) {
        List<String> data = FileHelper.readFromFile(fileName);
        return data.stream().noneMatch(line -> line.startsWith(id + ","));
    }
}