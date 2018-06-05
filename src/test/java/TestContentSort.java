import org.junit.Assert;
import org.junit.Test;
import test.directoryinfo.model.Content;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestContentSort {

    private static final boolean printTestDeclarations = true;

    /*
     * Проверка для директорий
     */
    @Test
    public void testDirectoryAndFileGreater() {
        Content c1 = new Content("m2.txt",100,0);
        Content c2 = new Content("onedir",-1,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    public void testDirectoryAndFileLesser() {
        Content c1 = new Content("onedir",-1,0);
        Content c2 = new Content("m2.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }

    @Test
    public void testDirectoriesEqual() {
        Content c1 = new Content("onedir",-1,0);
        Content c2 = new Content("onedir",-1,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "==");
        }
        Assert.assertTrue(c1.compareTo(c2) == 0);
    }


    @Test
    public void testDirectoriesGreater() {
        Content c1 = new Content("onedir12",-1,0);
        Content c2 = new Content("onedir11",-1,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    public void testDirectoriesLesser() {
        Content c1 = new Content("onedir12_1",-1,0);
        Content c2 = new Content("onedir13",-1,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }

    /*
     * Файлы: случаи равенства и неравенства значений
     */
    @Test
    public void testEqualFiles() {

        Content c1 = new Content("mine.txt",100,0);
        Content c2 = new Content("mine.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "==");
        }
        Assert.assertTrue(c1.compareTo(c2) == 0);
    }

    @Test
    public void testEqualWithDifferentCases() {

        Content c1 = new Content("mine.txt",100,0);
        Content c2 = new Content("MINE.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "==");
        }
        Assert.assertTrue(c1.compareTo(c2) == 0);
    }

    @Test
    public void testNotEqualFiles() {

        Content c1 = new Content("one.txt",100,0);
        Content c2 = new Content("another.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "!=");
        }
        Assert.assertTrue(c1.compareTo(c2) != 0);
    }

    /*
     * Тестирование файлов
     */
    @Test
    public void testWithNameAsPrefix() {

        Content c1 = new Content("example1.txt",100,0);
        Content c2 = new Content("example.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }


    @Test
    public void testGreaterWithExtensions() {

        Content c1 = new Content("mine.txt",100,0);
        Content c2 = new Content("m2.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    public void testLesserWithExtensions() {

        Content c1 = new Content("lord.txt",100,0);
        Content c2 = new Content("Yard.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }

    @Test
    public void testGreaterWithDigits() {

        Content c1 = new Content("m2_1.txt",100,0);
        Content c2 = new Content("m2.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    public void testLesserWithDigits() {

        Content c1 = new Content("m1_1.txt",100,0);
        Content c2 = new Content("m00002.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }


    @Test
    public void testLesserWithDigitAndLetter() {

        Content c1 = new Content("M1_1.txt",100,0);
        Content c2 = new Content("yard.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }

    @Test
    public void testGreaterWithDigitAndLetter() {

        Content c1 = new Content("letter.txt",100,0);
        Content c2 = new Content("D2.txt",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    /*
     * Проверка расширений
     */
    @Test
    public void testGreaterWithEqualNamesAndDifferentExtensions() {

        Content c1 = new Content("letter.txt",100,0);
        Content c2 = new Content("letter.pdf",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    @Test
    public void testLesserWithEqualNamesAndDifferentExtensions() {

        Content c1 = new Content("letter.a1",100,0);
        Content c2 = new Content("letter.a2",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, "<");
        }
        Assert.assertTrue(c1.compareTo(c2) < 0);
    }

    @Test
    public void testExtensionComesAfterLackOfOne() {

        Content c1 = new Content("letter.extension",100,0);
        Content c2 = new Content("letter",100,0);
        if (printTestDeclarations) {
            printComparisonTest(c1, c2, ">");
        }
        Assert.assertTrue(c1.compareTo(c2) > 0);
    }

    /*
     * Проверка сортировок
     */
    @Test
    public void testFullSortFromExample() {

        List<Content> actual = Arrays.asList(
                new Content("function.cpp", 3570, 0),
                new Content("f0008.doc", 26780197, 0),
                new Content("F4_00127.pdf", 124567, 0),
                new Content("f4_99.JPG", 1500000, 0),
                new Content("F1.txt", 124567, 0),
                new Content("f.txt", 4280, 0),
                new Content("X-FILES", -1, 0),
                new Content("innerTemp", -1, 0)
        );
        List<Content> expected = Arrays.asList(
                new Content("innerTemp", -1, 0),
                new Content("X-FILES", -1, 0),
                new Content("f.txt", 4280, 0),
                new Content("F1.txt", 124567, 0),
                new Content("f4_99.JPG", 1500000, 0),
                new Content("F4_00127.pdf", 124567, 0),
                new Content("f0008.doc", 26780197, 0),
                new Content("function.cpp", 3570, 0)
        );

        if (printTestDeclarations) {
            printSortTest(expected, actual);
        }

        Collections.sort(actual);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

    }

    @Test
    public void testFullSortFromExampleWithExtensions() {

        List<Content> actual = Arrays.asList(
                new Content("function.cpp", 3570, 0),
                new Content("f0008.pdf", 16780197, 0),
                new Content("f0008.doc", 26780197, 0),
                new Content("f0008", 36780197, 0),
                new Content("F4_00127.pdf", 124567, 0),
                new Content("f4_99.JPG", 1500000, 0),
                new Content("F1.txt", 124567, 0),
                new Content("f.txt", 4280, 0),
                new Content("X-FILES", -1, 0),
                new Content("innerTemp", -1, 0)
        );
        List<Content> expected = Arrays.asList(
                new Content("innerTemp", -1, 0),
                new Content("X-FILES", -1, 0),
                new Content("f.txt", 4280, 0),
                new Content("F1.txt", 124567, 0),
                new Content("f4_99.JPG", 1500000, 0),
                new Content("F4_00127.pdf", 124567, 0),
                new Content("f0008", 36780197, 0),
                new Content("f0008.doc", 26780197, 0),
                new Content("f0008.pdf", 16780197, 0),
                new Content("function.cpp", 3570, 0)
        );

        if (printTestDeclarations) {
            printSortTest(expected, actual);
        }

        Collections.sort(actual);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());

    }


    @Test
    public void testFullSortDirsWithDigits() {

        List<Content> actual = Arrays.asList(
                new Content("images1.rar", 10000, 0),
                new Content("images2014_2015", -1, 0),
                new Content("images2014_001", -1, 0),
                new Content("images2014", -1, 0),
                new Content("images2", -1, 0),
                new Content("images1", -1, 0),
                new Content("images", -1, 0)
        );
        List<Content> expected = Arrays.asList(
                new Content("images", -1, 0),
                new Content("images1", -1, 0),
                new Content("images2", -1, 0),
                new Content("images2014", -1, 0),
                new Content("images2014_001", -1, 0),
                new Content("images2014_2015", -1, 0),
                new Content("images1.rar", 10000, 0)
        );

        if (printTestDeclarations) {
            printSortTest(expected, actual);
        }

        Collections.sort(actual);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }


    private void printComparisonTest(Content c1, Content c2, String signString) {

        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + ": " + c1.printContentName() +  " " + signString + " " + c2.printContentName());
        System.out.println();

    }

    private void printSortTest(List<Content> expected,  List<Content> actual) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + ": ");
        actual.stream().map(Content::printContentName).forEachOrdered(System.out::println);
        System.out.println("\n---->\n");
        expected.stream().map(Content::printContentName).forEachOrdered(System.out::println);
        System.out.println();
    }

}

