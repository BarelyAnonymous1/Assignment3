import java.io.RandomAccessFile;

import student.TestCase;

/**
 * @author CS3114 staff
 * @version 1
 */

public class MergesortTest extends TestCase
{

    /**
     * This method sets up the tests that follow.
     */
    public void setUp()
    {
        // no op
    }

    public void testInit() throws Exception
    {
        Mergesort merge = new Mergesort();
        assertNotNull(merge);
        Mergesort.main(null);
    }

    /**
     * This method tests the main functionality of Mergesort on an "ascii" file
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testMergesortAscii4() throws Exception
    {
        FileGenerator generator = new FileGenerator();
        String[] args = new String[3];
        String[] genargs = new String[3];
        args[0] = "input12a.txt";
        args[1] = "15"; // Buffer pool size
        args[2] = "statFile.txt";
        genargs[0] = "-a";
        genargs[1] = "input12a.txt";
        genargs[2] = "1000";
        // String numBlocks = "100"; // Test file size
        generator.generateFile(genargs);
        Mergesort.main(args);

    }
}