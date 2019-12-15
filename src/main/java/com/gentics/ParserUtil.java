package com.gentics;

public final class ParserUtil {
    public static String toString(int codePoint) {
        return new String(new int[]{codePoint}, 0, 1);
    }
}
