package io.github.mrbonk97.analyzer.utils;

import java.util.regex.Pattern;

public class StringUtils {
    final static String DEFAULT_REPO_URL = null;
    final static String DEFAULT_RULESETS = "rulesets/java/quickstart.xml";
    final static String DEFAULT_REPORT_DIR = "report";
    final static String DEFAULT_MAX_THREAD = String.valueOf(Runtime.getRuntime().availableProcessors());

    public static String getRepoNameFromUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Please provide a valid github repository URL");
        }


        if (!Pattern.matches("^https://github\\.com/[^/]+/[^/]+(\\.git)$", url)) {
            throw new IllegalArgumentException("Please provide a valid github repository URL");
        }

        String[] arr = url.split("/");
        return arr[arr.length - 1];
    }

    public static String[] getArguments(String[] args) {
        String[] arr = {DEFAULT_REPO_URL, DEFAULT_RULESETS, DEFAULT_REPORT_DIR, DEFAULT_MAX_THREAD};


        for (int i = 0; i < args.length; i++) {
            if ("--url".equals(args[i]) && i + 1 < args.length) {
                arr[0] = args[i + 1];
            }
            if ("--rulesets".equals(args[i]) && i + 1 < args.length) {
                arr[1] = args[i + 1];
            }

            if ("--report-file".equals(args[i]) && i + 1 < args.length) {
                arr[2] = args[i + 1];
            }

            if ("--max-thread".equals(args[i]) && i + 1 < args.length) {
                arr[3] = args[i + 1];
            }
        }

        return arr;
    }

}
