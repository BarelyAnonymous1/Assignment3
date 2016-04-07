import java.io.*;

/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class Mergesort
{

    public static int FILE_SIZE;

    /**
     * The entry point of the application
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        RandomAccessFile input;
        RandomAccessFile temp;
        try
        {
            int numBuffers = Integer.parseInt(args[1]);
            if (numBuffers < 1)
            {
                System.out.println(
                        "Please provide at least 1 buffer for the buffer pool");
                return;
            }
            input = new RandomAccessFile(args[0], "rw");
            temp = new RandomAccessFile("temp", "rw");
            temp.setLength(0); // empties the temp files used
            BufferPool pool = new BufferPool(Integer.parseInt(args[1]));
            Merger fileSort = new Merger();
            FILE_SIZE = (int) input.length();

            RuntimeStats.startTime = System.currentTimeMillis();
            fileSort.sort(pool, input, temp, 0, (FILE_SIZE - 4) / 4);
            pool.flushPool(args[2]);

            input.close();
            temp.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // bufferpool.writeRecord(i, bufferpool.getRecord(j, input), temp);

    /**
     * going to keep a collection of notes on how the indexing works in each
     * class so we can try to keep numbers consistent:
     * 
     * 1) when creating a buffer with a certain blockID, use the first record in
     * the block
     * 
     * 2) when looking for a specific block, ask for the nth block (0, 1, 2,
     * etc)
     * 
     * 3) the sort will have to divide the record number by the block size to
     * access a specific block
     * 
     * 4) the sort will also need to mod the record number by the block size to
     * determine which record within the block is needed
     */

}