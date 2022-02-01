package com.stenway.sml;

import com.stenway.wsv.WsvTokenizer;
import java.util.ArrayList;
import java.util.List;

public class SmlTokenizerNew {
    private final ArrayList<Integer> tokens = new ArrayList<>();
    private final ArrayList<int[]> lines = new ArrayList<>();
    private final List<List<Token>> linesNew = new ArrayList<>();

    protected int[] toArray(List<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }

    protected int[] toArrayNew(List<List<Token>> listOfAllLines) {
        ArrayList<Integer> allTokensInASingleArray = new ArrayList<>();

        for (List<Token> currentLine: listOfAllLines){
            for (Token currentToken : currentLine) {
                allTokensInASingleArray.add(currentToken.getTokenType().getValue());
                allTokensInASingleArray.add(currentToken.getTokenLength());
            }
        }

        return allTokensInASingleArray.stream().mapToInt(i -> i).toArray();
    }

    protected int[] toArrayNew(ArrayList<int[]> listOfAllLines) {
        ArrayList<Integer> allTokensInASingleArray = new ArrayList<>();

        for (int[] currentLine: listOfAllLines) {
            for (int value : currentLine) {
                allTokensInASingleArray.add(value);
            }
        }
        return allTokensInASingleArray.stream().mapToInt(i -> i).toArray();
    }

    // TODO: Old code should be removed
    protected ArrayList<int[]> splitIntoLines(int[] wsvTokens) {
        ArrayList<Integer> curLineTokens = new ArrayList<>();

        for (int i=0; i<wsvTokens.length/2; i++) {
            int tokenType = wsvTokens[i*2];
            int tokenLength = wsvTokens[i*2+1];
            curLineTokens.add(tokenType);
            curLineTokens.add(tokenLength);
            if (tokenType == WsvTokenizer.TokenTypeLineBreak) {
                int[] line = toArray(curLineTokens);
                lines.add(line);
                curLineTokens.clear();
            }
        }

        int[] lastLine = toArray(curLineTokens);
        lines.add(lastLine);

        return lines;
    }

    protected List<List<Token>> splitIntoLinesNew(int[] wsvTokens) {
        List<Token> tokensPerLine = new ArrayList<>();

        for (int i=0; i<wsvTokens.length/2; i++) {
            int tokenType = wsvTokens[i*2];
            int tokenLength = wsvTokens[i*2+1];

            Token curToken = new Token(tokenType, tokenLength);
            tokensPerLine.add(curToken);

            if (isTokenTypeALineBreak(tokenType)){
                linesNew.add(tokensPerLine);
                tokensPerLine = new ArrayList<>();
            }
        }

        linesNew.add(tokensPerLine);
        return linesNew;
    }


    // TODO: Old code should be removed
    protected int getValueCount(int[] line) {
        int count = 0;
        for (int i=0; i<line.length/2; i++) {
            int tokenType = line[i*2];
            if (isTokenTypeCountable(tokenType)) {
                count++;
            } else if (isTokenTypeAStringLineBreak(tokenType)) {
                count--;
            }
        }
        return count;
    }

    protected int getValueCountNew(List<Token> line) {
        int count = 0;
        for (Token currentToken : line) {
            int tokenType = currentToken.getTokenType().getValue();
            if (isTokenTypeCountable(tokenType)) {
                count++;
            } else if (isTokenTypeAStringLineBreak(tokenType)) {
                count--;
            }
        }
        return count;
    }

    private boolean isTokenTypeCountable(int tokenType) {
        return tokenType == TokenType.T03_TOKEN_TYPE_NULL.getValue() ||
                tokenType == TokenType.T04_TOKEN_TYPE_VALUE.getValue() ||
                tokenType == TokenType.T05_TOKEN_TYPE_STRING_START.getValue();
    }

    private boolean isTokenTypeAStringLineBreak(int tokenType){
        return tokenType == TokenType.T09_TOKEN_TYPE_STRING_LINE_BREAK.getValue();
    }

    private boolean isTokenTypeALineBreak(int tokenType){
        return tokenType == TokenType.T00_TOKEN_TYPE_LINE_BREAK.getValue();
    }

    private boolean isTokenTypePartOfStringGroup(int tokenType) {
        return tokenType == TokenType.T05_TOKEN_TYPE_STRING_START.getValue() ||
                tokenType == TokenType.T06_TOKEN_TYPE_STRING_END.getValue() ||
                tokenType == TokenType.T07_TOKEN_TYPE_STRING_TEXT.getValue() ||
                tokenType == TokenType.T08_TOKEN_TYPE_STRING_ESCAPED_DOUBLE_QUOTE.getValue() ||
                tokenType == TokenType.T09_TOKEN_TYPE_STRING_LINE_BREAK.getValue();
    }

    // TODO: Old code should be removed
    private void addToken(int tokenType, int length) {
        tokens.add(tokenType);
        tokens.add(length);
    }

    // TODO: Old code should be removed
    protected int tokenizeFirstValue(int[] line, int type) {
        int startIndex = 0;
        if (line[0] == TokenType.T01_TOKEN_TYPE_WHITESPACE.getValue()) {
            startIndex += 1;
            addToken(line[0], line[1]);
        }
        int firstValueTokenType = line[startIndex*2];
        if (firstValueTokenType == TokenType.T03_TOKEN_TYPE_NULL.getValue() || firstValueTokenType == TokenType.T04_TOKEN_TYPE_VALUE.getValue()) {
            addToken(type, line[startIndex*2+1]);
            startIndex++;
        } else {
            for (int i=startIndex; i<line.length/2; i++) {
                int tokenType = line[i*2];
                int tokenLength = line[i*2+1];
                if (!isTokenTypePartOfStringGroup(tokenType)) {
                    break;
                }
                addToken(type, tokenLength);
                startIndex++;
            }
        }
        return startIndex;
    }

    // TODO: Old code should be removed
    protected ArrayList<Integer> tokenizeLine(int[] line) {
        int count = getValueCount(line);
        int startIndex = 0;
        if (count == 1) {
            startIndex = tokenizeFirstValue(line, TokenType.T12_TOKEN_TYPE_ELEMENT.getValue());
        } else if (count > 1) {
            startIndex = tokenizeFirstValue(line, TokenType.T13_TOKEN_TYPE_ATTRIBUTE.getValue());
        }
        for (int i=startIndex; i<line.length/2; i++) {
            int tokenType = line[i*2];
            int tokenLength = line[i*2+1];
            addToken(tokenType, tokenLength);
        }
        return tokens;
    }

    // TODO: Old code should be removed
    public int[] tokenizeDocument(String text) {
        int[] wsvTokens = WsvTokenizer.tokenize(text);
        splitIntoLines(wsvTokens);
        for (int[] line : lines) {
            tokenizeLine(line);
        }
        return toArray(tokens);
    }

	public int[] tokenizeDocumentNew(String text) {
        int[] wsvTokens = WsvTokenizer.tokenize(text);
		List<List<Token>> outerList = splitIntoLinesNew(wsvTokens);

        for (List<Token> currentList : outerList){
            checkIfGivenLineContainsOnlyAStartOrEndElement(currentList);
        }
        return toArrayNew(outerList);
	}

    private void checkIfGivenLineContainsOnlyAStartOrEndElement(List<Token> currentLine){
        //    Important: If an SML-line contains only one value or two values and second value is a line break,
        //    then it is an opening or a closing element.
        int index = checkIfGivenValueIsAnElement(currentLine);
        Token element;
        if(index >= 0){
            element = currentLine.get(index);
            // change TokenType of the token to "T12_TOKEN_TYPE_ELEMENT"
            if( element.getTokenType() != TokenType.T12_TOKEN_TYPE_ELEMENT){
                element.setTokenType(TokenType.T12_TOKEN_TYPE_ELEMENT);
            }
        } else if (index == -1){
            element = currentLine.get(index * -1);
            // It's an attribute - change TokenType of the token to "T13_TOKEN_TYPE_ATTRIBUTE"
            if( element.getTokenType() != TokenType.T13_TOKEN_TYPE_ATTRIBUTE){
                element.setTokenType(TokenType.T13_TOKEN_TYPE_ATTRIBUTE);
            }
        }
    }

    private int checkIfGivenValueIsAnElement(List<Token> currentLine) {
        // Case 01: Contains only one value. This should be the End-Element.
        if(currentLine.size() == 1 &&
                currentLine.get(0).getTokenType().equals(TokenType.T04_TOKEN_TYPE_VALUE)){
            return 0;
        }
        // Case 02: Contains two values. First value is an element and second value is a line break.
        else if (currentLine.size() == 2 &&
                currentLine.get(0).getTokenType().equals(TokenType.T04_TOKEN_TYPE_VALUE) &&
                currentLine.get(1).getTokenType().equals(TokenType.T00_TOKEN_TYPE_LINE_BREAK)){
            return 0;
        }

        // Case 03: Contains two values. First value is an element and second value is an "end of text".
        else if (currentLine.size() == 2 &&
                currentLine.get(0).getTokenType().equals(TokenType.T04_TOKEN_TYPE_VALUE) &&
                currentLine.get(1).getTokenType().equals(TokenType.T10_TOKEN_TYPE_END_OF_TEXT)){
            return 0;
        }

        // Case 04: Contains three values. First value is one or many whitespaces. Second value is an element.
        //          Third value is a line break.
        else if (currentLine.size() == 3 &&
                currentLine.get(0).getTokenType().equals(TokenType.T01_TOKEN_TYPE_WHITESPACE) &&
                currentLine.get(1).getTokenType().equals(TokenType.T04_TOKEN_TYPE_VALUE) &&
                currentLine.get(2).getTokenType().equals(TokenType.T00_TOKEN_TYPE_LINE_BREAK)){
            return 1;
        }

        else if (currentLine.size() > 3 &&
                currentLine.get(0).getTokenType().equals(TokenType.T01_TOKEN_TYPE_WHITESPACE) &&
                currentLine.get(1).getTokenType().equals(TokenType.T04_TOKEN_TYPE_VALUE) &&
                currentLine.get(2).getTokenType().equals(TokenType.T01_TOKEN_TYPE_WHITESPACE)){
            return -1;
        }
        return -999;
    }

    public List<List<Token>> tokenizeDocumentNewWithResultAsTwoDimensionalListOfGivenTokens(String text) {
        int[] wsvTokens = WsvTokenizer.tokenize(text);
        return splitIntoLinesNew(wsvTokens);
    }

    public static int[] tokenize(String text) {
        return new SmlTokenizerNew().tokenizeDocument(text);
    }
}
