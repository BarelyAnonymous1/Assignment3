import java.io.*;

/**
 * The class containing the main method, the entry point of the application.
 * 
 * 
 * On my honor:
 * 
 * - I have not used source code obtained from another student, or any other
 * unauthorized source, either modified or unmodified.
 * 
 * - All source code and documentation used in my program is either my original
 * work, or was derived by me from the source code published in the textbook for
 * this course.
 * 
 * - I have not discussed coding details about this project with anyone other
 * than my partner (in the case of a joint submission), instructor, ACM/UPE
 * tutors or the TAs assigned to this course. I understand that I may discuss
 * the concepts of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes anything during
 * the discussion or modifies any computer file during the discussion. I have
 * violated neither the spirit nor letter of this restriction.
 * 
 * @author platt, jondef95
 * @version 1
 */
public class Mergesort
{

    /**
     * the size of the file that is worked with
     */
    public static int fileSize;

    /**
     * The entry point of the application This application creates and sorts a
     * randomly generated ASCII or binary file. These files are sorted using
     * MergeSort and a BufferPool to minimize disk I/O and help optimize
     * performance
     * 
     * a collection of notes on how the indexing works in each class for
     * consistent numbering:
     * 
     * 1) when creating a buffer with a certain blockID, the first record in the
     * block is used
     * 
     * 2) when looking for a specific block, the nth block (0, 1, 2, etc) is
     * asked for
     * 
     * 3) the sort divides the record number by the block size to access a
     * specific block
     * 
     * 4) the sort also mods the record number by the block size to determine
     * which record within the block is needed
     * 
     * @param args
     *            of the form {"filename", "numBuffers", "statfilename"}
     */
    public static void main(String[] args)
    {
        if (args == null)
        {
            System.out.println("Hello, World!");
            return;
        }
        RandomAccessFile input;
        RandomAccessFile temp;
        try
        {
            int numBuffers = Integer.parseInt(args[1]);
            input = new RandomAccessFile(args[0], "rw");
            RuntimeStats.file = args[0];
            RuntimeStats.numBuffers = numBuffers;
            temp = new RandomAccessFile("temp", "rw");
            temp.setLength(0); // empties the temp files used
            BufferPool pool = new BufferPool(numBuffers);
            Merger fileSort = new Merger(pool);
            FILE_SIZE = (int) input.length();

            RuntimeStats.startTime = System.currentTimeMillis();
            fileSort.sort(input, temp, 0, (FILE_SIZE - 4) / 4);
            pool.flushPool(args[2]);

            input.close();
            temp.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}