package io.github.mrbonk97.analyzer.utils;

import java.io.IOException;
import java.nio.file.*;

public class FileUtils {
    public static void deleteFile(String dir, String fileName) {
        Path filePath = Paths.get(dir, fileName);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.printf("Deleted file: %s%n", filePath);
            } else {
                System.out.printf("File not found, nothing to delete: %s%n", filePath);
            }
        } catch (IOException e) {
            System.err.printf("Failed to delete file %s: %s%n", filePath, e.getMessage());
        }
    }


}
