package io.github.mrbonk97.analyzer.utils;

import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.exception.ExceptionCode;

import java.io.IOException;
import java.nio.file.*;

public class FileUtils {
    public static void deleteFile(String dir, String fileName) {
        Path filePath = Paths.get(dir, fileName);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_TO_DELETE_FILE, fileName);
        }
    }

    public static long countJavaFileInDirectory(Path path) {
        if (!Files.exists(path)) {
            return 0;
        }

        try (var stream = Files.walk(path)) {
            return stream.filter(p -> Files.isRegularFile(p) && p.toString().endsWith(".java")).count();
        } catch (IOException e) {
            throw new CustomException(ExceptionCode.FAILED_TO_COUNT_JAVA_FILES, path.toString());
        }
    }
}
