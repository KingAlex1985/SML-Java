package com.stenway.sml;

public enum TokenType {

    T00_TOKEN_TYPE_LINE_BREAK(0),
    T01_TOKEN_TYPE_WHITESPACE(1),
    T02_TOKEN_TYPE_COMMENT(2),
    T03_TOKEN_TYPE_NULL(3),
    T04_TOKEN_TYPE_VALUE(4),
    T05_TOKEN_TYPE_STRING_START(5),
    T06_TOKEN_TYPE_STRING_END(6),
    T07_TOKEN_TYPE_STRING_TEXT(7),
    T08_TOKEN_TYPE_STRING_ESCAPED_DOUBLE_QUOTE(8),
    T09_TOKEN_TYPE_STRING_LINE_BREAK(9),
    T10_TOKEN_TYPE_END_OF_TEXT(10),
    T11_TOKEN_TYPE_ERROR(11),
    T12_TOKEN_TYPE_ELEMENT(12),
    T13_TOKEN_TYPE_ATTRIBUTE(13),
    T99_TOKEN_TYPE_UNKNOWN(99);

    private final int value;

    public int getValue() {
        return value;
    }

    TokenType(int value) {
        this.value = value;
    }

    public static TokenType identifyTokenType(int givenTokenInt){

        switch (givenTokenInt){
            case 0: return TokenType.T00_TOKEN_TYPE_LINE_BREAK;
            case 1: return TokenType.T01_TOKEN_TYPE_WHITESPACE;
            case 2: return TokenType.T02_TOKEN_TYPE_COMMENT;
            case 3: return TokenType.T03_TOKEN_TYPE_NULL;
            case 4: return TokenType.T04_TOKEN_TYPE_VALUE;
            case 5: return TokenType.T05_TOKEN_TYPE_STRING_START;
            case 6: return TokenType.T06_TOKEN_TYPE_STRING_END;
            case 7: return TokenType.T07_TOKEN_TYPE_STRING_TEXT;
            case 8: return TokenType.T08_TOKEN_TYPE_STRING_ESCAPED_DOUBLE_QUOTE;
            case 9: return TokenType.T09_TOKEN_TYPE_STRING_LINE_BREAK;
            case 10: return TokenType.T10_TOKEN_TYPE_END_OF_TEXT;
            case 11: return TokenType.T11_TOKEN_TYPE_ERROR;
            case 12: return TokenType.T12_TOKEN_TYPE_ELEMENT;
            case 13: return TokenType.T13_TOKEN_TYPE_ATTRIBUTE;
            default: return TokenType.T99_TOKEN_TYPE_UNKNOWN;

        }
    }
}