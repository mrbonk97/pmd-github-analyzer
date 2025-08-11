import io.github.mrbonk97.analyzer.utils.CommandUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

public class CommandUtilsTest {
    final String os = System.getProperty("os.name").toLowerCase();

    @Test
    @DisplayName("Run command with valid command")
    void test_runCommand_1() {
        String[] command;
        if (os.contains("windows")) {
            command = new String[]{"cmd", "/c", "echo", "hello"};
        } else {
            command = new String[]{"echo", "hello"};
        }

        assertDoesNotThrow(() -> CommandUtils.runCommand(command));

    }

    @Test
    @DisplayName("Run command and get Output with valid command")
    void test_runCommand_2() throws IOException, InterruptedException {
        String[] command;

        if (os.contains("windows")) {
            command = new String[]{"cmd", "/c", "echo", "hello"};
        } else {
            command = new String[]{"echo", "hello"};
        }

        List<String> output = CommandUtils.runCommandAndGetOutput(command);
        assertFalse(output.isEmpty());
        assertEquals("hello", output.getFirst());
    }
}
