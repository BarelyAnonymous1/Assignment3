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
        // try
        // {
        // input = new RandomAccessFile(args[0], "rw");
        // }
        // catch (IOException e)
        // {
        // System.out.println("File '" + args[0] + "' was not found");
        // }
        //
        // int numBuffers = Integer.parseInt(args[1]);
        // RandomAccessFile stats;
        // try
        // {
        // stats = new RandomAccessFile(args[2], "rw");
        // }
        // catch(IOException e)
        // {
        // System.out.println("There was an error");
        // }
        //
        // if (numBuffers < 1)
        // {
        // System.out.println(
        // "Please provide at least 1 buffer for the buffer pool");
        // return;
        // }

        // BufferPool sortPool = new BufferPool(numBuffers);
        try
        {
            input = new RandomAccessFile(args[0], "rw");
            temp = new RandomAccessFile("test.txt", "rw");
            BufferPool buffpool = new BufferPool(Integer.parseInt(args[1]));
            FILE_SIZE = (int) input.length();
            tempRec1 = new byte[4];
            tempRec2 = new byte[4];
            System.out.println(input);
            
            RuntimeStats.startTime = System.currentTimeMillis();
            
            sort(buffpool, input, temp, 0, (FILE_SIZE - 4) / 4);
            buffpool.flushPool();
            
            RuntimeStats.endTime = System.currentTimeMillis();
            double total = (RuntimeStats.endTime - RuntimeStats.startTime)
                    / 1000.0;
            System.out.println("Time: " + total);
            
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

    /**
     * The initial sorting point for the application -- where everything comes
     * in the door
     * 
     * @param pool
     *            the buffer pool we're working with
     * @param input
     *            the file where original content is
     * @param temp
     *            the holder file
     * @param left
     *            left side of mergesort
     * @param right
     *            right side of mergesort
     */
    public static void sort(BufferPool pool, RandomAccessFile input,
            RandomAccessFile temp, int left, int right)
    {
        if (left == right)
            return; // List has one record
        int mid = (left + right) / 2; // Select midpoint
        sort(pool, input, temp, left, mid); // Mergesort first half
        sort(pool, input, temp, mid + 1, right); // Mergesort second half
        for (int i = left; i <= right; i++) // Copy subarray to temp
        {
            // pool.writeRecord(i * 4, pool.getRecord(i * 4, input), temp);
            pool.getRecordTemp(4 * i, tempRec1, input);
            pool.writeRecordTemp(4 * i, tempRec1, temp);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
        for (int curr = left; curr <= right; curr++)
        {
            pool.getRecordTemp(4 * (i1), tempRec1, temp);
            pool.getRecordTemp(4 * (i2), tempRec2, temp);
            if (i1 == mid + 1) // Left sublist exhausted
            {
                // pool.writeRecord(curr * 4,
                // pool.getRecord(4 * (i2++), temp), input);
                pool.writeRecordTemp(4 * curr, tempRec2, input);
                // A[curr] = temp[i2++];
                i2++;
            }
            else if (i2 > right) // Right sublist exhausted
            {
                // pool.writeRecord(curr * 4,
                // pool.getRecord(4 * (i1++), temp), input);
                pool.writeRecordTemp(4 * curr, tempRec1, input);
                // A[curr] = temp[i1++];
                i1++;
            }
            // compareByteArray(pool.getRecord(4 * i1, temp),
            // pool.getRecord(4 * i2, temp)) <= 0

            else if (compareByteArray(tempRec1, tempRec2) <= 0) // Get smaller
                                                                // value
            {
                // pool.writeRecord(curr * 4,
                // pool.getRecord(4 * (i1++), temp), input);
                pool.writeRecordTemp(4 * curr, tempRec1, input);
                // A[curr] = temp[i1++];
                i1++;
            }

            else
            {
                // pool.writeRecord(curr * 4,
                // pool.getRecord(4 * (i2++), temp), input);
                pool.writeRecordTemp(4 * curr, tempRec2, input);
                // A[curr] = temp[i2++];
                i2++;
            }
        }
    }

    private static int compareByteArray(byte[] obj, byte[] comp)
    {
        if (obj[0] < comp[0] || (obj[0] == comp[0] && obj[1] < comp[1]))
            return -1;
        else if (obj[0] > comp[0]
                || (obj[0] == comp[0] && obj[1] > comp[1]))
            return 1;
        else
            return 0;
    }

}