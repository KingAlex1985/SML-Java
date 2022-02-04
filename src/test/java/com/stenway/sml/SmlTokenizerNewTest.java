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

        assertTrue(diffInMillisecond > 0);
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

    @Test
    public void toArrayNew01() {
        String testText = complexTestString01();
        int[] wsvTokens = WsvTokenizer.tokenize(testText);

        List<List<Token>> linesWithTokensNew = classUnderTest.splitIntoLinesNew(wsvTokens);
        int[] intArrayNewImpl = classUnderTest.toArrayNew(linesWithTokensNew);

        int[] intArrayNewExpected = new int[]{4, 13, 0, 1, 1, 3, 4, 6, 0, 1, 1, 7, 4, 16, 1, 1, 4, 3, 0, 1, 1, 7, 4, 17, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 1, 1, 4, 2, 0, 1, 1, 3, 4, 3, 0, 1, 1, 3, 4, 16, 1, 1, 5, 1, 7, 11, 6, 1, 0, 1, 4, 3, 10, 0};

        assertArrayEquals(intArrayNewExpected, intArrayNewImpl);
    }

    @Test
    public void testIsTokenTypePartOfStringGroup() {
        assertFalse(classUnderTest.isTokenTypePartOfStringGroup(4));
        assertTrue(classUnderTest.isTokenTypePartOfStringGroup(5));
        assertTrue(classUnderTest.isTokenTypePartOfStringGroup(6));
        assertTrue(classUnderTest.isTokenTypePartOfStringGroup(7));
        assertTrue(classUnderTest.isTokenTypePartOfStringGroup(8));
        assertTrue(classUnderTest.isTokenTypePartOfStringGroup(9));
        assertFalse(classUnderTest.isTokenTypePartOfStringGroup(10));
    }

    @Test
    public void testToArrayNew() {

        int[] line01 = new int[]{4, 13, 10, 0};
        int[] line02 = new int[]{1, 3, 10, 0};

        List<int[]> listOfAllLines = new ArrayList<>();
        listOfAllLines.add(line01);
        listOfAllLines.add(line02);

        int[] resultIntArray = classUnderTest.toIntArrayNew(listOfAllLines);
        assertEquals(8, resultIntArray.length);

        assertEquals(4, resultIntArray[0]);
        assertEquals(13, resultIntArray[1]);
        assertEquals(10, resultIntArray[2]);
        assertEquals(0, resultIntArray[3]);
        assertEquals(1, resultIntArray[4]);
        assertEquals(3, resultIntArray[5]);
        assertEquals(10, resultIntArray[6]);
        assertEquals(0, resultIntArray[7]);

    }
}