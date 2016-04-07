import java.io.*;

/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class Mergesort
{

    public static int     FILE_SIZE;
    private static byte[] tempRec1;
    private static byte[] tempRec2;

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
            input = new RandomAccessFile(args[0], "rw");
        }
        catch (IOException e)
        {
            System.out.println("File '" + args[0] + "' was not found");
        }

        int numBuffers = Integer.parseInt(args[1]);
        
        RandomAccessFile statFile;
        try
        {
            statFile = new RandomAccessFile(args[2], "rw");
        }
        catch (IOException e)
        {
            System.out.println("There was an error");
        }

        if (numBuffers < 1)
        {
            System.out.println(
                    "Please provide at least 1 buffer for the buffer pool");
            return;
        }

        input = new RandomAccessFile(args[0], "rw");
        temp = new RandomAccessFile("test.txt", "rw");
        RuntimeStats stats = new RuntimeStats("input1.txt", 15);
        BufferPool pool = new BufferPool(Integer.parseInt(args[1]));
        FILE_SIZE = (int) input.length();
        
        tempRec1 = new byte[4];
        tempRec2 = new byte[4];
        
        RuntimeStats.startTime = System.currentTimeMillis();
        
        sort(pool, input, temp, 0, (FILE_SIZE - 4) / 4);
        pool.flushPool();
        
        RuntimeStats.endTime = System.currentTimeMillis();
        System.out.println(stats.toString());
        statFile.writeBytes(stats.toString());

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