package com.yiduo.maven.plugin.sftp;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(String text) {
        return null == text || text.isEmpty();
    }

    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }
}
