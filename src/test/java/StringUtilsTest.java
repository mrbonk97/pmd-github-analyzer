import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

public class StringUtilsTest {

    @Test
    @DisplayName("Normalize Url")
    void test_normalizeRepositoryUrl_1() {
        String url = "https://github.com/apache/commons-lang.git";
        assertEquals("https://github.com/apache/commons-lang.git", StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Convert Github Url to end with .git")
    void test_normalizeRepositoryUrl_2() {
        String url = "https://github.com/apache/commons-lang";
        assertEquals("https://github.com/apache/commons-lang.git", StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Convert Github Url trim both side")
    void test_normalizeRepositoryUrl_3() {
        String url = "  https://github.com/apache/commons-lang ";
        assertEquals("https://github.com/apache/commons-lang.git", StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Convert Github Url ending with slash")
    void test_normalizeRepositoryUrl_4() {
        String url = "https://github.com/apache/commons-lang/";
        assertEquals("https://github.com/apache/commons-lang.git", StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Throw Exception when invalid Url is provided 1")
    void test_normalizeRepositoryUrl_5() {
        String url = "https://github.com/apache";
        assertThrows(CustomException.class, () -> StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Throw Exception when invalid Url is provided 1")
    void test_normalizeRepositoryUrl_6() {
        String url = "https://github.com/apache/";
        assertThrows(CustomException.class, () -> StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Throw Exception when invalid Url is provided 1")
    void test_normalizeRepositoryUrl_7() {
        String url = "https://github.com/apache.git";
        assertThrows(CustomException.class, () -> StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Throw Exception when invalid Url is provided 1")
    void test_normalizeRepositoryUrl_8() {
        String url = "github.com/apache/commons-lang.git";
        assertThrows(CustomException.class, () -> StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Throw Exception when invalid Url is provided 1")
    void test_normalizeRepositoryUrl_9() {
        String url = "https://www.naver.com";
        assertThrows(CustomException.class, () -> StringUtils.normalizeRepositoryUrl(url));
    }

    @Test
    @DisplayName("Extract repository name from url 1")
    void test_getRepoNameFromUrl_1() {
        String url = "https://github.com/apache/commons-lang.git";
        assertEquals("commons-lang.git", StringUtils.getRepoNameFromUrl(url));
    }

    @Test
    @DisplayName("Extract repository name from url 2")
    void test_getRepoNameFromUrl_2() {
        String url = "https://github.com/apache/commons-lang";
        assertEquals("commons-lang", StringUtils.getRepoNameFromUrl(url));
    }




//    @Test
//    @DisplayName("Extract repository URL from options")
//    void test_getOptions_1() {
//        String[] args = {"--url", "https://github.com/mrbonk97/pmd-github-analyzer.git"};
//        String[] options = StringUtils.getOptions(args);
//        assertEquals("https://github.com/mrbonk97/pmd-github-analyzer.git", options[0]);
//    }
//
//    @Test
//    @DisplayName("Extract null when option order is incorrect")
//    void test_getOptions_2() {
//        String[] args = {"https://github.com/mrbonk97/pmd-github-analyzer.git", "--url"};
//        String[] options = StringUtils.getOptions(args);
//        assertNull(options[0]);
//    }
//
//    @Test
//    @DisplayName("Extract null when option order is incorrect")
//    void test_getOptions_3() {
//        String[] args = {};
//        String[] options = StringUtils.getOptions(args);
//        assertNull(options[0]);
//    }

}
