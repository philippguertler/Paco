package com.gentics;

import org.junit.jupiter.api.Test;

import static com.gentics.ParserAssertions.assertThat;

public class ChainTest {
    @Test
    void strings() {
        Parser<String> p1 = Parser.string("abc");
        Parser<String> p2 = Parser.string("def");
        Parser<String> chained = p1.chain(res -> p2.map(res2 -> res + res2));
        assertThat(chained.parse("abcdef")).equalsResult("abcdef");
    }
}
