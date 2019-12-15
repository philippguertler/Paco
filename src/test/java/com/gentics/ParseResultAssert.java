package com.gentics;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseResultAssert<T> extends AbstractAssert<ParseResultAssert<T>, ParseResult<T>> {
    public ParseResultAssert(ParseResult<T> actual) {
        super(actual, ParseResultAssert.class);
    }

    public ParseResultAssert<T> equalsResult(T result) {
        assertThat(actual.result()).isEqualTo(result);
        return this;
    }

    public ParseResultAssert<T> hasIndex(int i) {
        assertThat(actual.index()).isEqualTo(i);
        return this;
    }

    public ParseResultAssert<T> isFailure() {
        assertThat(actual.isSuccess()).isFalse();
        return this;
    }
}
