package com.stenway.sml;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenTypeTest {

    @Test
    public void getValue() {
        assertEquals(13, TokenType.T13_TOKEN_TYPE_ATTRIBUTE.getValue());
    }

    @Test
    public void identifyTokenType() {
        assertEquals(TokenType.T00_TOKEN_TYPE_LINE_BREAK, TokenType.identifyTokenType(0));
        assertEquals(TokenType.T01_TOKEN_TYPE_WHITESPACE, TokenType.identifyTokenType(1));
        assertEquals(TokenType.T02_TOKEN_TYPE_COMMENT, TokenType.identifyTokenType(2));
        assertEquals(TokenType.T03_TOKEN_TYPE_NULL, TokenType.identifyTokenType(3));
        assertEquals(TokenType.T04_TOKEN_TYPE_VALUE, TokenType.identifyTokenType(4));
        assertEquals(TokenType.T05_TOKEN_TYPE_STRING_START, TokenType.identifyTokenType(5));
        assertEquals(TokenType.T06_TOKEN_TYPE_STRING_END, TokenType.identifyTokenType(6));
        assertEquals(TokenType.T07_TOKEN_TYPE_STRING_TEXT, TokenType.identifyTokenType(7));
        assertEquals(TokenType.T08_TOKEN_TYPE_STRING_ESCAPED_DOUBLE_QUOTE, TokenType.identifyTokenType(8));
        assertEquals(TokenType.T09_TOKEN_TYPE_STRING_LINE_BREAK, TokenType.identifyTokenType(9));
        assertEquals(TokenType.T10_TOKEN_TYPE_END_OF_TEXT, TokenType.identifyTokenType(10));
        assertEquals(TokenType.T11_TOKEN_TYPE_ERROR, TokenType.identifyTokenType(11));
        assertEquals(TokenType.T12_TOKEN_TYPE_ELEMENT, TokenType.identifyTokenType(12));
        assertEquals(TokenType.T13_TOKEN_TYPE_ATTRIBUTE, TokenType.identifyTokenType(13));
        assertEquals(TokenType.T99_TOKEN_TYPE_UNKNOWN, TokenType.identifyTokenType(99));

        assertNotEquals(TokenType.T13_TOKEN_TYPE_ATTRIBUTE, TokenType.identifyTokenType(14));
    }
}