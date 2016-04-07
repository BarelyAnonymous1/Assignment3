import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class Merger
{
    private static byte[] tempRec1;
    private static byte[] tempRec2;

    public Merger()
    {
        tempRec1 = new byte[4];
        tempRec2 = new byte[4];
    }

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
    public void sort(BufferPool pool, RandomAccessFile input,
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
//            compareByteArray(tempRec1, tempRec2) <= 0
            else if (compareByteArray2(tempRec1, tempRec2)) // Get smaller
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

    private int compareByteArray(byte[] obj, byte[] comp)
    {
        if (obj[0] < comp[0] || (obj[0] == comp[0] && obj[1] < comp[1]))
            return -1;
        else if (obj[0] > comp[0]
                || (obj[0] == comp[0] && obj[1] > comp[1]))
            return 1;
        else
            return 0;
    }
    
    private boolean compareByteArray2(byte[] obj, byte[] comp)
    {
        short objNum = ByteBuffer.wrap(obj).getShort();
        short compNum = ByteBuffer.wrap(comp).getShort();
        return objNum <= compNum;
    }
}
