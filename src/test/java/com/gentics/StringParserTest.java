package com.gentics;

import org.junit.jupiter.api.Test;

import static com.gentics.ParserAssertions.assertThat;

class StringParserTest {

    @Test
    void string() {
        Parser<String> parser = Parser.string("hey");
        assertThat(parser.parse("hey")).equalsResult("hey");

        assertThat(parser.parsePartially("heya", 0))
            .equalsResult("hey")
            .hasIndex(3);

        assertThat(parser.parse("bogus")).isFailure().hasIndex(0);

        assertThat(parser.parse("he")).isFailure().hasIndex(2);
    }
}