package com.gentics;

import org.junit.jupiter.api.Test;

import static com.gentics.ParserAssertions.assertThat;

public class CombinatorTest {
    @Test
    void testTrim() {
        Parser<String> parser = Parser.digits.trim(Parser.whitespace);
        assertThat(parser.tryParse("123")).isEqualTo("123");
        assertThat(parser.tryParse("       123")).isEqualTo("123");
        assertThat(parser.tryParse("       123   ")).isEqualTo("123");
        assertThat(parser.tryParse("123   ")).isEqualTo("123");
    }

    @Test
    void testTakeUntil() {

    }
}
