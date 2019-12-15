package com.gentics;

import org.assertj.core.api.AbstractAssert;

public class ParserAssert<T> extends AbstractAssert<ParserAssert<T>, Parser<T>> {
    public ParserAssert(Parser<T> actual) {
        super(actual, ParserAssert.class);
    }
}
