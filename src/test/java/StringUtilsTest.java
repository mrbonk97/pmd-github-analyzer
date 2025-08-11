import io.github.mrbonk97.analyzer.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringUtilsTest {

    @Test
    // Test: extract correct repository name
    void test_getRepoNameFromUrl_1() {
        String url = "https://github.com/apache/commons-lang.git";
        assertEquals("commons-lang.git", StringUtils.getRepoNameFromUrl(url));
    }

    @Test
    // Test: throw when invalid url is provided
    void test_getRepoNameFromUrl_2() {
        String url = "https://www.naver.com";
        assertThrows(IllegalArgumentException.class, () -> StringUtils.getRepoNameFromUrl(url));
    }

    @Test
    // Test: throw when URI doesn't with .git
    void test_getRepoNameFromUrl_3() {
        String url = "https://github.com/apache/commons-lang";
        assertThrows(IllegalArgumentException.class, () -> StringUtils.getRepoNameFromUrl(url));
    }

}
