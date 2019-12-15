package com.gentics;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Parser<T> {
    ParseResult<T> parsePartially(String input, int startIndex);

    default ParseResult<T> parsePartially(String input) {
        return parsePartially(input, 0);
    }

    default ParseResult<T> parse(String input) {
        ParseResult<T> result = parsePartially(input, 0);
        if (!result.isSuccess()) {
            return result;
        } else if (result.index() < input.length()) {
            return ParseResult.error("Parser was finished before the input ended.", input, result.index());
        } else if (result.index() > input.length()) {
            return ParseResult.error("Invalid parser found. Index after parsing was greater than the input length.", input, result.index());
        } else {
            return result;
        }
    }

    default Optional<T> parseOpt(String input) {
        return Optional.ofNullable(parse(input).result());
    }

    default T tryParse(String input) {
        ParseResult<T> result = parse(input);
        if (result.isSuccess()) {
            return result.result();
        } else {
            throw (ParseError) result;
        }
    }

    default <R> Parser<R> map(Function<T, R> mapper) {
        return chain(mapper.andThen(Parser::success));
    }

    default <R> Parser<R> chain(Function<T, Parser<R>> mapper) {
        return (input, index) -> {
            ParseResult<T> first = parsePartially(input, index);
            if (!first.isSuccess()) {
                return (ParseResult<R>) first;
            }
            return mapper.apply(first.result()).parsePartially(input, first.index());
        };
    }

    default <R> Parser<R> flatMap(Function<T, Parser<R>> mapper) {
        return chain(mapper);
    }

    default <R> Parser<R> then(Parser<R> nextParser) {
        return chain(ignore -> nextParser);
    }

    default Parser<T> skip(Parser<?> nextParser) {
        return chain(result -> nextParser.map(ignore -> result));
    }

    default Parser<T> trim(Parser<?> otherParser) {
        return otherParser.then(this).skip(otherParser);
    }

    default Parser<T> wrap(Parser<?> before, Parser<?> after) {
        return before.then(this).skip(after);
    }

    default Parser<T> desc(String description) {
        return (input, index) -> {
            ParseResult<T> result = parsePartially(input, index);
            if (result.isSuccess()) {
                return result;
            } else {
                return ParseResult.error(description, input, index);
            }
        };
    }

    Parser<String> whitespace = takeWhile(Character::isWhitespace);
    Parser<String> digits = takeWhile(Character::isDigit);


    static <T> Parser<T> success(T result) {
        return (input, index) -> ParseResult.success(result, input, index);
    }

    static Parser<String> string(String expected) {
        return (input, index) -> {
            for (int i = 0; i < expected.length(); i++) {
                if (index + i >= input.length()) {
                    return ParseResult.error("Input ended early", input, index + i);
                }
                if (input.codePointAt(index + i) != expected.codePointAt(i)) {
                    return ParseResult.error("Expected character " + ParserUtil.toString(expected.codePointAt(i)), input, index + i);
                }
            }
            return ParseResult.success(expected, input, index + expected.length());
        };
    }

    static Parser<String> takeWhile(Predicate<Integer> predicate) {
        return (input, index) -> {
            StringBuilder result = new StringBuilder();
            for (; index < input.length(); index++) {
                int cp = input.codePointAt(index);
                if (predicate.test(cp)) {
                    result.appendCodePoint(cp);
                } else {
                    return ParseResult.success(result.toString(), input, index);
                }
            }
            return ParseResult.success(result.toString(), input, index);
        };
    }

    static <T> Parser<T> alt(Parser<T> ...parsers) {
        return (input, index) -> {
            for (Parser<T> parser : parsers) {
                ParseResult<T> result = parser.parsePartially(input, index);
                if (result.isSuccess()) {
                    return result;
                }
            }
            return ParseResult.error("None of the parsers matched the input", input, index);
        };
    }

    default Parser<String> takeUntil(Parser<?> otherParser) {
        return (input, index) -> {
            for (int i = index; i < input.length(); i++) {
                if (otherParser.parsePartially(input, index).isSuccess()) {
                    return ParseResult.success(input.substring(index, i), input, input.length());
                }
            }
            return ParseResult.success(input.substring(index), input, input.length());
        };
    }
}
