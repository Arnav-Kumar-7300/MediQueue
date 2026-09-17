package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String DATA_FOLDER = "data";

    // Create data folder if it does not exist
    public static void createDataFolder() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // Write complete list of lines to a file
    public static void writeToFile(
            String fileName,
            List<String> lines)
            throws IOException {

        createDataFolder();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {

            for (String line : lines) {

                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Read all lines from a file
    public static List<String> readFromFile(
            String fileName)
            throws IOException {

        createDataFolder();

        List<String> lines =
                new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
            return lines;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    // Add one line to the end of a file
    public static void appendToFile(
            String fileName,
            String line)
            throws IOException {

        createDataFolder();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     fileName,
                                     true))) {

            writer.write(line);
            writer.newLine();
        }
    }

    // Delete all contents of a file
    public static void clearFile(
            String fileName)
            throws IOException {

        createDataFolder();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {
        }
    }
}