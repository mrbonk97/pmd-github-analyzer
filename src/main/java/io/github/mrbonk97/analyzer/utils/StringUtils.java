package io.github.mrbonk97.analyzer.utils;

import io.github.mrbonk97.analyzer.exception.CustomException;
import io.github.mrbonk97.analyzer.exception.ExceptionCode;

import java.util.regex.Pattern;

public class StringUtils {
  public static String normalizeRepositoryUrl(String url) {
        if (url == null) {
            throw new CustomException(ExceptionCode.NO_URL);
        }

        url = url.trim();

        if (!Pattern.matches("^https://github\\.com/[^/]+/[^/]+(?:\\.git)?/?$", url)) {
            throw new CustomException(ExceptionCode.INVALID_URL, url);
        }

        if (url.endsWith(".git")) {
            return url;
        }

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url + ".git";
    }

    public static String getRepoNameFromUrl(String url) {
        if (url == null) {
            throw new CustomException(ExceptionCode.NO_URL);
        }

        String[] arr = url.split("/");
        return arr[arr.length - 1];
    }
}
