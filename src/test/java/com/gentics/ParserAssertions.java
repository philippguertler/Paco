package com.gentics;

import org.assertj.core.api.Assertions;

public class ParserAssertions extends Assertions {
    public static <T> ParseResultAssert<T> assertThat(ParseResult<T> actual) {
        return new ParseResultAssert<>(actual);
    }
}
