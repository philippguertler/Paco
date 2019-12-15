package com.gentics;

public interface ParseResult<T> {
    boolean isSuccess();
    String input();
    int index();
    T result();
    String errorMessage();

    static <T> ParseResult<T> success(T result, String input, int index) {
        return new ParseResultSuccess<>(result, input, index);
    }

    static <T> ParseResult<T> error(String message, String input, int index) {
        return (ParseResult<T>) new ParseError(message, input, index);
    }
}
