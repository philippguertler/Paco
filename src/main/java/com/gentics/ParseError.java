package com.gentics;

public class ParseError extends RuntimeException implements ParseResult<Object> {
    private final String input;
    private final int index;

    public ParseError(String message, String input, int index) {
        super(message);
        this.input = input;
        this.index = index;
    }

    @Override
    public String input() {
        return input;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public Object result() {
        return null;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public String errorMessage() {
        return getMessage();
    }
}
