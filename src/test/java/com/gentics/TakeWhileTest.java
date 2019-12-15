package com.gentics;

import org.junit.jupiter.api.Test;

import static com.gentics.ParserAssertions.assertThat;

public class TakeWhileTest {
    @Test
    void takeWhile() {
        assertThat(Parser.takeWhile(ignore -> true).tryParse("abc")).isEqualTo("abc");
        assertThat(Parser.takeWhile(ignore -> false).parsePartially("abc"))
            .equalsResult("")
            .hasIndex(0);

        assertThat(Parser.takeWhile(Character::isAlphabetic).parsePartially("ab1"))
            .equalsResult("ab")
            .hasIndex(2);
    }
}
