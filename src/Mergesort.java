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
            BufferPool bufpool = new BufferPool(1);
            bufpool.newBuffer(1, null);
            bufpool.newBuffer(1, input);
            bufpool.getBuffer(1, input).storeBlock();
            byte[] output = bufpool.getRecord(input, 8188);
            System.out.println(output[1]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

//    public void sort(RandomAccessFile input, RandomAccessFile temp,
//            int left, int right)
//    {
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
//    }
}