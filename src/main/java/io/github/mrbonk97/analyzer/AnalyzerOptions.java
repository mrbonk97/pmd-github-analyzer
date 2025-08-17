package io.github.mrbonk97.analyzer;

import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.exception.ExceptionCode;
import io.github.mrbonk97.analyzer.utils.StringUtils;

public class AnalyzerOptions {
    public String url;
    public String rulesets = "rulesets/java/quickstart.xml";
    public String reportFile = "report";
    public int maxThread = Runtime.getRuntime().availableProcessors();

    public AnalyzerOptions(String[] args) {
        int len = args.length;

        for (int i = 0; i < len; i++) {
            switch (args[i]) {
                case "--url":
                    if (i + 1 < len) this.url = StringUtils.normalizeRepositoryUrl(args[++i]);
                    break;
                case "--rulesets":
                    if (i + 1 < len) this.rulesets = args[++i];
                    break;
                case "--report-file":
                    if (i + 1 < len) this.reportFile = args[++i];
                    break;
                case "--max-thread":
                    if (i + 1 < len) {
                        try {
                            this.maxThread = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            throw new CustomException(ExceptionCode.INVALID_MAX_THREAD_OPTION);
                        }
                    } else {
                        throw new CustomException(ExceptionCode.INVALID_OPTION);
                    }
                    break;
                default:
                    throw new CustomException(ExceptionCode.INVALID_OPTION, args[i]);

            }
        }
    }
}
