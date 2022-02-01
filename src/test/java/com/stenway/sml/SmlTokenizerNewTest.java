package com.stenway.sml;

import com.stenway.wsv.WsvTokenizer;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SmlTokenizerNewTest {

    SmlTokenizerNew classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new SmlTokenizerNew();
    }

    @Test
    public void tokenizeDocumentTest01() {
        String testText = "BlaBlubText";
        int[] intArrayExpected = {12,11,10,0};
        int[] intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);

        testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "End";
        intArrayExpected = new int[]{12, 11, 10, 0, 12, 11, 10, 0, 12, 7, 0, 1, 1, 2, 13, 4, 1, 1, 4, 6, 0, 1, 1, 2, 13, 2, 1, 1, 4, 5, 0, 1, 1, 2, 13, 9, 1, 1, 4, 10, 1, 1, 4, 5, 0, 1, 1, 2, 13, 4, 1, 1, 5, 1, 7, 20, 6, 1, 0, 1, 12, 3, 10, 0};
        intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentTest02() {
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "  GPS 123 456 789\n" +
                "End";

        int[] intArrayExpected = new int[]{12, 7, 0, 1, 1, 2, 13, 4, 1, 1, 4, 6, 0, 1, 1, 2, 13, 2, 1, 1, 4, 5, 0, 1, 1, 2, 13, 9, 1, 1, 4, 10, 1, 1, 4, 5, 0, 1, 1, 2, 13, 4, 1, 1, 5, 1, 7, 20, 6, 1, 0, 1, 1, 2, 13, 3, 1, 1, 4, 3, 1, 1, 4, 3, 1, 1, 4, 3, 0, 1, 12, 3, 10, 0};
        int[] intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertEquals(74, intArrayActual.length);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentTest03() {
        String testText = "M";

        int[] intArrayExpected = new int[]{12, 1, 10, 0};
        int[] intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertEquals(4, intArrayActual.length);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentTest04() {
        String testText = "Mabol";

        int[] intArrayExpected = new int[]{12, 5, 10, 0};
        int[] intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertEquals(4, intArrayActual.length);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentTest05() {
        String testText = "\"Mabol and Babol\"";

        int[] intArrayExpected = new int[]{12, 1, 12, 15, 12, 1, 10, 0};
        int[] intArrayActual = classUnderTest.tokenizeDocument(testText);
        assertEquals(8, intArrayActual.length);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentTest06() {
        // testing the execution time
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "  GPS 123 456 789\n" +
                "End";

        StringBuilder timeTestText = new StringBuilder();

        for (int i = 0; i < 10000; i++){
            timeTestText.append(testText);
        }

        Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println("Start Timestamp: " + startTimestamp);

        classUnderTest.tokenizeDocument(timeTestText.toString());

        Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println("End Timestamp: " + startTimestamp);

        long diffInMillisecond = endTimestamp.getTime() - startTimestamp.getTime();
//        System.out.println("Diff in milliseconds:" + diffInMillisecond);
        System.out.println("" + diffInMillisecond);
    }

    @Test
    public void tokenizeDocumentNewTest02() {
        // testing the execution time
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "  GPS 123 456 789\n" +
                "End";

        StringBuilder timeTestText = new StringBuilder();

        for (int i = 0; i < 10000; i++){
            timeTestText.append(testText);
        }

        Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println("Start Timestamp: " + startTimestamp);

        classUnderTest.tokenizeDocumentNew(timeTestText.toString());

        Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println("End Timestamp: " + startTimestamp);

        long diffInMillisecond = endTimestamp.getTime() - startTimestamp.getTime();
//        System.out.println("Diff in milliseconds:" + diffInMillisecond);
        System.out.println("" + diffInMillisecond);
    }

    @Test
    public void tokenizeDocumentNewTest03() {
        int[] intArrayExpected = classUnderTest.tokenizeDocumentNew("");
        assertEquals(2, intArrayExpected.length);
        assertEquals(10, intArrayExpected[0]);
        assertEquals(0, intArrayExpected[1]);
    }

    @Test
    public void tokenizeTest01() {
        String testText = "BlaBlubText";
        int[] intArrayExpected = {12,11,10,0};
        int[] intArrayActual = SmlTokenizer.tokenize(testText);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeTest02() {
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "End";

        int[] intArrayExpected = new int[]{12, 7, 0, 1, 1, 2, 13, 4, 1, 1, 4, 6, 0, 1, 1, 2, 13, 2, 1, 1, 4, 5, 0, 1, 1, 2, 13, 9, 1, 1, 4, 10, 1, 1, 4, 5, 0, 1, 1, 2, 13, 4, 1, 1, 5, 1, 7, 20, 6, 1, 0, 1, 12, 3, 10, 0};
        int[] intArrayActual = SmlTokenizer.tokenize(testText);
        assertArrayEquals("Its not what we expected.",intArrayExpected, intArrayActual);
    }

    @Test
    public void splitIntoLines() {
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "  GPS 123 456\n" +
                "End";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        ArrayList<int[]> linesWithTokens = classUnderTest.splitIntoLines(wsvTokens);

        assertEquals("The amount of lines is not correct.",7, linesWithTokens.size());
    }

    @Test
    public void splitIntoLines02() {
        String testText = "Mabol";

        ArrayList<int[]> expectedLinesWithTokens = new ArrayList<>();
        int[] intArray01 = {4, 5, 10, 0};
        expectedLinesWithTokens.add(intArray01);

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        ArrayList<int[]> actualLinesWithTokens = classUnderTest.splitIntoLines(wsvTokens);

        assertEquals("The amount of lines is not correct.",1, actualLinesWithTokens.size());
        assertArrayEquals(expectedLinesWithTokens.get(0),actualLinesWithTokens.get(0));
    }

    @Test
    public void splitIntoLines03() {
//        This is the text that is used within the int-Arrays
//                "MyRootElement\n"
//                "   Group1\n"
//                "       MyFirstAttribute 123\n"
//                "       MySecondAttribute 10 20 30 40 50\n"
//                "   End\n"
//                "   MyThirdAttribute \"Hello world\"\n"
//                "End";

        String testText = complexTestString01();

        ArrayList<int[]> expectedLinesWithTokens = new ArrayList<>();
        int[] intArray01 = {4, 13, 0, 1};
        int[] intArray02 = {1, 3, 4, 6, 0, 1};
        int[] intArray03 = {1, 7, 4, 16, 1, 1, 4, 3, 0, 1};
        int[] intArray04 = {1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1};
        int[] intArray05 = {1, 3, 4, 3, 0, 1};
        int[] intArray06 = {1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1};
        int[] intArray07 = {4, 3, 10, 0};

        expectedLinesWithTokens.add(intArray01);
        expectedLinesWithTokens.add(intArray02);
        expectedLinesWithTokens.add(intArray03);
        expectedLinesWithTokens.add(intArray04);
        expectedLinesWithTokens.add(intArray05);
        expectedLinesWithTokens.add(intArray06);
        expectedLinesWithTokens.add(intArray07);

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        ArrayList<int[]> actualLinesWithTokens = classUnderTest.splitIntoLines(wsvTokens);

        assertEquals("The amount of lines is not correct.",7, actualLinesWithTokens.size());
        assertArrayEquals(expectedLinesWithTokens.get(0),actualLinesWithTokens.get(0));
        assertArrayEquals(expectedLinesWithTokens.get(1),actualLinesWithTokens.get(1));
        assertArrayEquals(expectedLinesWithTokens.get(2),actualLinesWithTokens.get(2));
        assertArrayEquals(expectedLinesWithTokens.get(3),actualLinesWithTokens.get(3));
        assertArrayEquals(expectedLinesWithTokens.get(4),actualLinesWithTokens.get(4));
        assertArrayEquals(expectedLinesWithTokens.get(5),actualLinesWithTokens.get(5));
        assertArrayEquals(expectedLinesWithTokens.get(6),actualLinesWithTokens.get(6));
    }

    @Test
    public void splitIntoLinesNew() {
        String testText = "Message\n" +
                "  From Andrew\n" +
                "  To James\n" +
                "  Timestamp 2021-01-02 15:43\n" +
                "  Text \"I'll be there at 5pm\"\n" +
                "  GPS 123 456 789\n" +
                "End";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",7, linesWithTokens.size());

        List<Token> line01 = linesWithTokens.get(0);
        assertEquals("Amount of tokens is not correct.", 2, line01.size());
        assertEquals(4,line01.get(0).getTokenType().getValue());
        assertEquals(0,line01.get(1).getTokenType().getValue());


        List<Token> line02 = linesWithTokens.get(1);
        assertEquals("Amount of tokens is not correct.", 5, line02.size());

        List<Token> line03 = linesWithTokens.get(2);
        assertEquals("Amount of tokens is not correct.", 5, line03.size());

        List<Token> line04 = linesWithTokens.get(3);
        assertEquals("Amount of tokens is not correct.", 7, line04.size());

        List<Token> line05 = linesWithTokens.get(4);
        assertEquals("Amount of tokens is not correct.", 7, line05.size());

        List<Token> line06 = linesWithTokens.get(5);
        assertEquals("Amount of tokens is not correct.", 9, line06.size());

        List<Token> line07 = linesWithTokens.get(6);
        assertEquals("Amount of tokens is not correct.", 2, line07.size());
    }

    @Test
    public void splitIntoLinesNew02() {
        String testText = "Mabol";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",1, linesWithTokens.size());

    }

    @Test
    public void splitIntoLinesNew03() {
        String testText = "" +
                "MyRootElement\n" +
                "   Group1\n" +
                "       MyFirstAttribute 123\n" +
                "       MySecondAttribute 10 20 30 40 50\n" +
                "   End\n" +
                "   MyThirdAttribute \"Hello world\"\n" +
                "   MyFourthAttribute \"A string with \"\"double quotes\"\"\"\n" +
                "End";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",8, linesWithTokens.size());
        assertEquals("The amount of tokens is not correct.",2, linesWithTokens.get(0).size());
        assertEquals("The amount of tokens is not correct.",3, linesWithTokens.get(1).size());
        assertEquals("The amount of tokens is not correct.",5, linesWithTokens.get(2).size());
        assertEquals("The amount of tokens is not correct.",13, linesWithTokens.get(3).size());
        assertEquals("The amount of tokens is not correct.",3, linesWithTokens.get(4).size());
        assertEquals("The amount of tokens is not correct.",7, linesWithTokens.get(5).size());
        assertEquals("The amount of tokens is not correct.",10, linesWithTokens.get(6).size());
        assertEquals("The amount of tokens is not correct.",2, linesWithTokens.get(7).size());
    }

    @Test
    public void splitIntoLinesNew04() {
        String testText = "" +
                "# Comment test\n" +
                "\"My Root Element\"\n" +
                "  \"My First Attribute\" 123\n" +
                "   MyFourthAttribute \"A string with \"\"double quotes\"\"\"\n" +
                "End";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",5, linesWithTokens.size());
        assertEquals("The amount of tokens is not correct.",2, linesWithTokens.get(0).size());
        assertEquals("The amount of tokens is not correct.",4, linesWithTokens.get(1).size());
        assertEquals("The amount of tokens is not correct.",7, linesWithTokens.get(2).size());
        assertEquals("The amount of tokens is not correct.",10, linesWithTokens.get(3).size());
        assertEquals("The amount of tokens is not correct.",2, linesWithTokens.get(4).size());
    }

//    complexTestString01();

    @Test
    public void splitIntoLinesNew05() {
        String testText = complexTestString01();

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",7, linesWithTokens.size());
        assertEquals("The amount of tokens is not correct.",2, linesWithTokens.get(0).size());
        assertEquals("The amount of tokens is not correct.",3, linesWithTokens.get(1).size());
        assertEquals("The amount of tokens is not correct.",5, linesWithTokens.get(2).size());
        assertEquals("The amount of tokens is not correct.",13, linesWithTokens.get(3).size());
        assertEquals("The amount of tokens is not correct.",3, linesWithTokens.get(4).size());
    }

    @Test
    public void compareOldToNewImplementationOfSplitIntoLines01() {
        String testText = complexTestString01();
        int[] wsvTokens = WsvTokenizer.tokenize(testText);

        ArrayList<int[]> linesWithTokens = classUnderTest.splitIntoLines(wsvTokens);
        int[] intArrayOldImpl = classUnderTest.toArrayNew(linesWithTokens);

        List<List<Token>> linesWithTokensNew = classUnderTest.splitIntoLinesNew(wsvTokens);
        int[] intArrayNewImpl = classUnderTest.toArrayNew(linesWithTokensNew);

        int[] intArrayOldExpected = new int[]{4, 13, 0, 1, 1, 3, 4, 6, 0, 1, 1, 7, 4, 16, 1, 1, 4, 3, 0, 1, 1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 4, 3, 0, 1, 1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 4, 3, 10, 0};
        int[] intArrayNewExpected = new int[]{4, 13, 0, 1, 1, 3, 4, 6, 0, 1, 1, 7, 4, 16, 1, 1, 4, 3, 0, 1, 1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 4, 3, 0, 1, 1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 4, 3, 10, 0};

        assertArrayEquals(intArrayOldExpected, intArrayOldImpl);
        assertArrayEquals(intArrayNewExpected, intArrayNewImpl);

        assertArrayEquals("Old implementation and new implementation of tokenizeDocument-Method should give the same result back.", intArrayOldImpl, intArrayNewImpl);

    }

    @Test
    public void getValueCount01() {

//        This is the text that is used within the int-Arrays
//                "MyRootElement\n"
//                "   Group1\n"
//                "       MyFirstAttribute 123\n"
//                "       MySecondAttribute 10 20 30 40 50\n"
//                "   End\n"
//                "   MyThirdAttribute \"Hello world\"\n"
//                "End";


        int[] intArray01 = {4, 13, 0, 1};
        int[] intArray02 = {1, 3, 4, 6, 0, 1};
        int[] intArray03 = {1, 7, 4, 16, 1, 1, 4, 3, 0, 1};
        int[] intArray04 = {1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1};
        int[] intArray05 = {1, 3, 4, 3, 0, 1};
        int[] intArray06 = {1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1};
        int[] intArray07 = {4, 3, 10, 0};


        int valueCount01 = classUnderTest.getValueCount(intArray01);
        assertEquals("The count is not correct", 1, valueCount01);

        int valueCount02 = classUnderTest.getValueCount(intArray02);
        assertEquals("The count is not correct", 1, valueCount02);

        int valueCount03 = classUnderTest.getValueCount(intArray03);
        assertEquals("The count is not correct", 2, valueCount03);

        int valueCount04 = classUnderTest.getValueCount(intArray04);
        assertEquals("The count is not correct", 6, valueCount04);

        int valueCount05 = classUnderTest.getValueCount(intArray05);
        assertEquals("The count is not correct", 1, valueCount05);

        int valueCount06 = classUnderTest.getValueCount(intArray06);
        assertEquals("The count is not correct", 2, valueCount06);

        int valueCount07 = classUnderTest.getValueCount(intArray07);
        assertEquals("The count is not correct", 1, valueCount07);
    }

    @Test
    public void getValueCountNew01() {
        String testText = "" +
                "MyRootElement\n" +
                "   Group1\n" +
                "       MyFirstAttribute 123\n" +
                "       MySecondAttribute 10 20 30 40 50\n" +
                "   End\n" +
                "   MyThirdAttribute \"Hello world\"\n" +
                "End";

        int[] wsvTokens = WsvTokenizer.tokenize(testText);
        List<List<Token>> linesWithTokens = classUnderTest.splitIntoLinesNew(wsvTokens);

        assertEquals("The amount of lines is not correct.",7, linesWithTokens.size());

        List<Token> line01 = linesWithTokens.get(0);
        assertEquals("Amount of tokens is not correct.", 2, line01.size());
        assertEquals(4,line01.get(0).getTokenType().getValue());
        assertEquals(0,line01.get(1).getTokenType().getValue());

        int valueCount01 = classUnderTest.getValueCountNew(line01);
        assertEquals("The count is not correct", 1, valueCount01);

        List<Token> line02 = linesWithTokens.get(1);
        int valueCount02 = classUnderTest.getValueCountNew(line02);
        assertEquals("The count is not correct", 1, valueCount02);

        List<Token> line03 = linesWithTokens.get(2);
        int valueCount03 = classUnderTest.getValueCountNew(line03);
        assertEquals("The count is not correct", 2, valueCount03);

        List<Token> line04 = linesWithTokens.get(3);
        int valueCount04 = classUnderTest.getValueCountNew(line04);
        assertEquals("The count is not correct", 6, valueCount04);

        List<Token> line05 = linesWithTokens.get(4);
        int valueCount05 = classUnderTest.getValueCountNew(line05);
        assertEquals("The count is not correct", 1, valueCount05);

        List<Token> line06 = linesWithTokens.get(5);
        int valueCount06 = classUnderTest.getValueCountNew(line06);
        assertEquals("The count is not correct", 2, valueCount06);

        List<Token> line07 = linesWithTokens.get(6);
        int valueCount07 = classUnderTest.getValueCountNew(line07);
        assertEquals("The count is not correct", 1, valueCount07);

    }

    @Test
    public void toArray() {
        List<Integer> givenList = Arrays.asList(12, 7, 0, 1, 1, 2, 13, 4, 1, 1, 4, 6, 0, 1, 1, 2, 13, 2, 1, 1, 4, 5, 0, 1, 1, 2, 13, 9, 1, 1, 4, 10, 1, 1, 4, 5, 0, 1, 1, 2, 13, 4, 1, 1, 5, 1, 7, 20, 6, 1, 0, 1, 1, 2, 13, 3, 1, 1, 4, 3, 1, 1, 4, 3, 1, 1, 4, 3, 0, 1, 12, 3, 10, 0);

        int[] intArrayExpected = new int[]{12, 7, 0, 1, 1, 2, 13, 4, 1, 1, 4, 6, 0, 1, 1, 2, 13, 2, 1, 1, 4, 5, 0, 1, 1, 2, 13, 9, 1, 1, 4, 10, 1, 1, 4, 5, 0, 1, 1, 2, 13, 4, 1, 1, 5, 1, 7, 20, 6, 1, 0, 1, 1, 2, 13, 3, 1, 1, 4, 3, 1, 1, 4, 3, 1, 1, 4, 3, 0, 1, 12, 3, 10, 0};
        int[] intArrayActual = classUnderTest.toArray(givenList);

        assertArrayEquals(intArrayExpected, intArrayActual);

    }

    @Test
    public void tokenizeLine() {

//        This is the text that is used within the int-Arrays
//                "MyRootElement\n"
//                "   Group1\n"
//                "       MyFirstAttribute 123\n"
//                "       MySecondAttribute 10 20 30 40 50\n"
//                "   End\n"
//                "   MyThirdAttribute \"Hello world\"\n"
//                "End";

        int[] intArray01 = {4, 13, 0, 1};
        int[] intArray02 = {1, 3, 4, 6, 0, 1};
        int[] intArray03 = {1, 7, 4, 16, 1, 1, 4, 3, 0, 1};
        int[] intArray04 = {1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1};
        int[] intArray05 = {1, 3, 4, 3, 0, 1};
        int[] intArray06 = {1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1};
        int[] intArray07 = {4, 3, 10, 0};

        ArrayList<Integer> actualTokensOfOneLine;
        List<Integer> expectedTokensOfOneLine;

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(12,13,0,1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray01);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(1, 3, 12, 6, 0, 1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray02);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(1, 7, 13, 16, 1, 1, 4, 3, 0, 1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray03);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray04);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(1, 3, 12, 3, 0, 1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray05);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(1, 3, 13, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray06);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);

        classUnderTest = new SmlTokenizerNew();
        expectedTokensOfOneLine = Arrays.asList(12, 3, 10, 0);
        actualTokensOfOneLine = classUnderTest.tokenizeLine(intArray07);
        assertEquals(expectedTokensOfOneLine, actualTokensOfOneLine);
    }

    @Test
    public void tokenizeDocumentTest07() {
//                This is the text that is used within the int-Arrays
//                "MyRootElement\n"
//                "   Group1\n"
//                "       MyFirstAttribute 123\n"
//                "       MySecondAttribute 10 20 30 40 50\n"
//                "   End\n"
//                "   MyThirdAttribute \"Hello world\"\n"
//                "End";

        int[] intArrayActual = classUnderTest.tokenizeDocument(complexTestString01());
        int[] intArrayExpected = new int[]{12, 13, 0, 1, 1, 3, 12, 6, 0, 1, 1, 7, 13, 16, 1, 1, 4, 3, 0, 1, 1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 12, 3, 0, 1, 1, 3, 13, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 12, 3, 10, 0};

        assertArrayEquals(intArrayExpected, intArrayActual);
    }

    @Test
    public void tokenizeDocumentNewTest01() {
//        This is the text that is used within the int-Arrays
//                "MyRootElement\n"
//                "   Group1\n"
//                "       MyFirstAttribute 123\n"
//                "       MySecondAttribute 10 20 30 40 50\n"
//                "   End\n"
//                "   MyThirdAttribute \"Hello world\"\n"
//                "End";

        int[] intArrayActual = classUnderTest.tokenizeDocumentNew(complexTestString01());
        int[] intArrayExpected = new int[]{12, 13, 0, 1, 1, 3, 12, 6, 0, 1, 1, 7, 13, 16, 1, 1, 4, 3, 0, 1, 1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 12, 3, 0, 1, 1, 3, 13, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 12, 3, 10, 0};

        assertArrayEquals(intArrayExpected, intArrayActual);
    }

    @Test
    public void compareOldToNewImplementationOfTokenizeDocument() {

        int[] intArrayOldImpl = classUnderTest.tokenizeDocument(complexTestString01());
        int[] intArrayNewImpl = classUnderTest.tokenizeDocumentNew(complexTestString01());

        int[] intArrayOldExpected = new int[]{12, 13, 0, 1, 1, 3, 12, 6, 0, 1, 1, 7, 13, 16, 1, 1, 4, 3, 0, 1, 1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 12, 3, 0, 1, 1, 3, 13, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 12, 3, 10, 0};
        int[] intArrayNewExpected = new int[]{12, 13, 0, 1, 1, 3, 12, 6, 0, 1, 1, 7, 13, 16, 1, 1, 4, 3, 0, 1, 1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 12, 3, 0, 1, 1, 3, 13, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 12, 3, 10, 0};

        assertArrayEquals(intArrayOldExpected, intArrayOldImpl);
        assertArrayEquals(intArrayNewExpected, intArrayNewImpl);

        assertArrayEquals("Old implementation and new implementation of tokenizeDocument-Method should give the same result back.", intArrayOldImpl, intArrayNewImpl);

    }

    @Test
    public void compareOldToNewImplementationOfTokenizeDocument02() {

        int[] intArrayOldImpl = classUnderTest.tokenizeDocument(complexTestString02());
        int[] intArrayNewImpl = classUnderTest.tokenizeDocumentNew(complexTestString02());

        int[] intArrayOldExpected = new int[]{1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 10, 0};
        int[] intArrayNewExpected = new int[]{1, 7, 13, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 10, 0};

        assertArrayEquals(intArrayOldExpected, intArrayOldImpl);
        assertArrayEquals(intArrayNewExpected, intArrayNewImpl);

        assertArrayEquals("Old implementation and new implementation of tokenizeDocument-Method should give the same result back.", intArrayOldImpl, intArrayNewImpl);

    }

    @Test
    public void compareOldToNewImplementationOfTokenizeDocument03() {
        int[] intArrayOldImpl = classUnderTest.tokenizeDocument("");
        assertEquals(2, intArrayOldImpl.length);
        assertEquals(10, intArrayOldImpl[0]);
        assertEquals(0, intArrayOldImpl[1]);

        int[] intArrayNewImpl = classUnderTest.tokenizeDocumentNew("");
        assertEquals(2, intArrayNewImpl.length);
        assertEquals(10, intArrayNewImpl[0]);
        assertEquals(0, intArrayNewImpl[1]);

        assertArrayEquals("Old implementation and new implementation of tokenizeDocument-Method should give the same result back.", intArrayOldImpl, intArrayNewImpl);
    }

    private String complexTestString01(){
        return "" +
                "MyRootElement\n" +
                "   Group1\n" +
                "       MyFirstAttribute 123\n" +
                "       MySecondAttribute 10 20 30 40 50\n" +
                "   End\n" +
                "   MyThirdAttribute \"Hello world\"\n" +
                "End";
    }

    private String complexTestString02(){
        return "" +
                "       MySecondAttribute 10 20 30 40 50\n";
    }

    @Test
    public void tokenizeDocumentNewWithResultAsTwoDimensionalListOfGivenTokens() {
        List<List<Token>> twoDimensionalList = classUnderTest.tokenizeDocumentNewWithResultAsTwoDimensionalListOfGivenTokens(complexTestString01());

        assertEquals(7, twoDimensionalList.size());
    }

    @Test
    public void tokenize() {
        int[] intArrayTokens = SmlTokenizerNew.tokenize(complexTestString01());

        assertEquals(70, intArrayTokens.length);
    }
}