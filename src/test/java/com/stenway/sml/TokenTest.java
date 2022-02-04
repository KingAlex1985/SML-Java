package com.stenway.sml;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenTest {

    Token classUnderTest;

    @Test
    public void getTokenType() {
        classUnderTest = new Token(4, 15);
        assertEquals(TokenType.T04_TOKEN_TYPE_VALUE,classUnderTest.getTokenType());
        assertEquals(15,classUnderTest.getTokenLength());
    }

    @Test
    public void setTokenType() {
        classUnderTest = new Token(4, 15);
        assertEquals(TokenType.T04_TOKEN_TYPE_VALUE,classUnderTest.getTokenType());
        assertEquals(15,classUnderTest.getTokenLength());

        classUnderTest.setTokenType(TokenType.T01_TOKEN_TYPE_WHITESPACE);
        assertEquals(TokenType.T01_TOKEN_TYPE_WHITESPACE,classUnderTest.getTokenType());
        assertEquals(15,classUnderTest.getTokenLength());
    }

    @Test
    public void getTokenLength() {
        classUnderTest = new Token(TokenType.T04_TOKEN_TYPE_VALUE, 15);
        assertEquals(TokenType.T04_TOKEN_TYPE_VALUE,classUnderTest.getTokenType());
        assertEquals(15,classUnderTest.getTokenLength());
    }

    @Test
    public void testToString() {
        classUnderTest = new Token(TokenType.T04_TOKEN_TYPE_VALUE, 15);
        assertEquals("{4, 15}", classUnderTest.toString());
    }
}