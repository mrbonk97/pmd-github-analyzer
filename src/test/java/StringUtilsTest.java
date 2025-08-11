import io.github.mrbonk97.analyzer.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

public class StringUtilsTest {

    @Test
    @DisplayName("Extract correct repository name from valid URL")
    void test_getRepoNameFromUrl_1() {
        String url = "https://github.com/apache/commons-lang.git";
        assertEquals("commons-lang.git", StringUtils.getRepoNameFromUrl(url));
    }

    @Test
    @DisplayName("Throw IllegalArgumentException when invalid URL is provided")
    void test_getRepoNameFromUrl_2() {
        String url = "https://www.naver.com";
        assertThrows(IllegalArgumentException.class, () -> StringUtils.getRepoNameFromUrl(url));
    }

    @Test
    @DisplayName("Throw IllegalArgumentException when URL does not end with .git")
    void test_getRepoNameFromUrl_3() {
        String url = "https://github.com/apache/commons-lang";
        assertThrows(IllegalArgumentException.class, () -> StringUtils.getRepoNameFromUrl(url));
    }


    @Test
    @DisplayName("Extract repository URL from options")
    void test_getOptions_1() {
        String[] args = {"--url", "https://github.com/mrbonk97/pmd-github-analyzer.git"};
        String[] options = StringUtils.getOptions(args);
        assertEquals("https://github.com/mrbonk97/pmd-github-analyzer.git", options[0]);
    }

    @Test
    @DisplayName("Extract null when option order is incorrect")
    void test_getOptions_2() {
        String[] args = {"https://github.com/mrbonk97/pmd-github-analyzer.git", "--url"};
        String[] options = StringUtils.getOptions(args);
        assertNull(options[0]);
    }

    @Test
    @DisplayName("Extract null when option order is incorrect")
    void test_getOptions_3() {
        String[] args = {};
        String[] options = StringUtils.getOptions(args);
        assertNull(options[0]);
    }

}
