import java.io.*;

/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class Mergesort
{

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
            input = new RandomAccessFile("input.txt", "rw");
            temp = new RandomAccessFile("test.txt", "rw");
            BufferPool bufpool = new BufferPool(5);
            bufpool.newBuffer(0,  temp);
            byte[] output = bufpool.getRecord(8188, input);
            bufpool.writeRecord(8188, output, temp);
            System.out.println(output[0]);
            System.out.println(output[1]);
            System.out.println(output[2]);
            System.out.println(output[3]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    
    //bufferpool.writeRecord(i, bufferpool.getRecord(j, input), temp);

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

    public void sort(BufferPool pool, RandomAccessFile input,
            RandomAccessFile temp, int left, int right)
    {
//        if (left == right)
//            return; // List has one record
//        int mid = (left + right) / 2; // Select midpoint
//        sort(input, temp, left, mid); // Mergesort first half
//        sort(input, temp, mid + 1, right); // Mergesort second half
//        for (int i = left; i <= right; i++) // Copy subarray to temp
//            temp[i] = input[i];
//        // Do the merge operation back to A
//        int i1 = left;
//        int i2 = mid + 1;
//        for (int curr = left; curr <= right; curr++)
//        {
//            if (i1 == mid + 1) // Left sublist exhausted
//                A[curr] = temp[i2++];
//            else if (i2 > right) // Right sublist exhausted
//                A[curr] = temp[i1++];
//            else if (temp[i1] <= temp[i2]) // Get smaller value
//                A[curr] = temp[i1++];
//            else
//                A[curr] = temp[i2++];
//        }
    }
}