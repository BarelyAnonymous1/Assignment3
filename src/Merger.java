
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

    public Merger()
    {
        // TODO Auto-generated constructor stub
    }

//    public void mergesort(byte[] A, Comparable[] temp, int left,
//            int right)
//    {
//        if (left == right)
//            return; // List has one record
//        int mid = (left + right) / 2; // Select midpoint
//        mergesort(A, temp, left, mid); // Mergesort first half
//        mergesort(A, temp, mid + 1, right); // Mergesort second half
//        for (int i = left; i <= right; i++) // Copy subarray to temp
//            temp[i] = A[i];
//        // Do the merge operation back to A
//        int i1 = left;
//        int i2 = mid + 1;
//        for (int curr = left; curr <= right; curr++)
//        {
//            if (i1 == mid + 1) // Left sublist exhausted
//                A[curr] = temp[i2++];
//            else if (i2 > right) // Right sublist exhausted
//                A[curr] = temp[i1++];
//            else if (temp[i1].compareTo(temp[i2]) <= 0) // Get smaller value
//                A[curr] = temp[i1++];
//            else
//                A[curr] = temp[i2++];
//        }
//    }
}
