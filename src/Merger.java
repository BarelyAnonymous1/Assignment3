import java.io.*;
/**
 * The actual mergesort implementation for the program USING INTS FOR THE TIME
 * BEING/ DON'T KNOW WHAT IT WILL NEED TO BE
 * 
 * @author platt, jondef95
 * @version 1
 *
 */
public class Merger
{

    BufferPool pool;
    
    public Merger(BufferPool startPool)
    {
        pool = startPool;
    }

    public void mergesort(RandomAccessFile input, RandomAccessFile temp, int left,
            int right)
    {
//    {
//        if (left == right)
//            return; // List has one record
//        int mid = (left + right) / 2; // Select midpoint
//        mergesort(input, temp, left, mid); // Mergesort first half
//        mergesort(input, temp, mid + 1, right); // Mergesort second half
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
