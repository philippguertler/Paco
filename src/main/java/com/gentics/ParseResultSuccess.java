package com.gentics;

import java.util.Objects;

public class ParseResultSuccess<T> implements ParseResult<T> {
    private final T result;
    private final String input;
    private final int index;

    public ParseResultSuccess(T result, String input, int index) {
        this.input = input;
        this.result = Objects.requireNonNull(result);
        this.index = index;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T result() {
        return result;
    }

    @Override
    public String input() {
        return input;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String errorMessage() {
        return null;
    }
}
