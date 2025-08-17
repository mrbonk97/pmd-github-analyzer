import io.github.mrbonk97.analyzer.AnalyzerOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyzerOptionsTest {

    @Test
    @DisplayName("Extract url option from args")
    void test_AnalyzerOptions_url() {
        String[] args = {"--url", "https://github.com/apache/commons-lang.git"};
        AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);


        assertEquals("https://github.com/apache/commons-lang.git", analyzerOptions.url);
    }

    @Test
    @DisplayName("Extract rulesets option from args")
    void test_AnalyzerOptions_rulesets() {
        String[] args = {"--rulesets", "TEST_RULESETS"};
        AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);


        assertEquals("TEST_RULESETS", analyzerOptions.rulesets);
    }

    @Test
    @DisplayName("Extract reportFile option from args")
    void test_AnalyzerOptions_reportFile() {
        String[] args = {"--report-file", "TEST_REPORT_FILE"};
        AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);


        assertEquals("TEST_REPORT_FILE", analyzerOptions.reportFile);
    }

    @Test
    @DisplayName("Extract maxThread option from args")
    void test_AnalyzerOptions_maxThread() {
        String[] args = {"--max-thread", "100"};
        AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);


        assertEquals(100, analyzerOptions.maxThread);
    }

    @Test
    @DisplayName("Extract Options")
    void test_AnalyzerOptions() {
        String[] args = {"--url", "https://github.com/apache/commons-lang.git",
                "--rulesets", "TEST_RULESETS",
                "--report-file", "TEST_REPORT_FILE",
                "--max-thread", "100"};

        AnalyzerOptions analyzerOptions = new AnalyzerOptions(args);


        assertEquals("https://github.com/apache/commons-lang.git", analyzerOptions.url);
        assertEquals("TEST_RULESETS", analyzerOptions.rulesets);
        assertEquals("TEST_REPORT_FILE", analyzerOptions.reportFile);
        assertEquals(100, analyzerOptions.maxThread);
    }
}


