package com.stenway.sml;

public class Token {
    private TokenType tokenType;
    private final int tokenLength;

    public Token(TokenType tokenType, int tokenLength){
        this.tokenType = tokenType;
        this.tokenLength = tokenLength;
    }

    public Token(int tokenTypeAsInt, int tokenLength){
        this.tokenType = TokenType.identifyTokenType(tokenTypeAsInt);
        this.tokenLength = tokenLength;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType){
        this.tokenType = tokenType;
    }

    public int getTokenLength() {
        return tokenLength;
    }

    @Override
    public String toString() {
        return "{" + tokenType.getValue() + ", " + tokenLength +'}';
    }
}
