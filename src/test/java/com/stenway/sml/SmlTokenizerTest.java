package com.stenway.sml;

import com.stenway.wsv.WsvTokenizer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SmlTokenizerTest {
    SmlTokenizer classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new SmlTokenizer();
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
        String testText = "" +
                "MyRootElement\n" +
                "   Group1\n" +
                "       MyFirstAttribute 123\n" +
                "       MySecondAttribute 10 20 30 40 50\n" +
                "   End\n" +
                "   MyThirdAttribute \"Hello world\"\n" +
                "End";

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
}
