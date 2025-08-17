import io.github.mrbonk97.analyzer.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {

    @Test
    @DisplayName("return 0 when directory doesn't exist")
    void test_FileUtils_1() {
        Path path = Paths.get("meflkmslkemrslekmr", "jawneajkwenkjadnaksd", "asdkmalsdm");
        assertEquals(0, FileUtils.countJavaFileInDirectory(path));
    }

    @Test
    @DisplayName("return >0 when directory has java files")
    void test_FileUtils_2() throws IOException {
        Path tempDir = Files.createTempDirectory("junit");
        Path javaFile = Files.createFile(tempDir.resolve("TestFile.java"));
        assertEquals(1, FileUtils.countJavaFileInDirectory(tempDir));
    }


}
