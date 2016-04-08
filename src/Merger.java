import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class Merger
{
    private byte[] tempRec1;
    private byte[] tempRec2;

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
            pool.getRecord(4 * i, tempRec1, input);
            pool.writeRecord(4 * i, tempRec1, temp);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
        pool.getRecord(4 * (i1), tempRec1, temp);
        pool.getRecord(4 * (i2), tempRec2, temp);
        for (int curr = left; curr <= right; curr++)
        {

            if (i1 == mid + 1) // Left sublist exhausted
            {
                pool.writeRecord(4 * curr, tempRec2, input);
                i2++;
                pool.getRecord(4 * (i2), tempRec2, temp);
                // A[curr] = temp[i2++];

            }
            else if (i2 > right) // Right sublist exhausted
            {
                pool.writeRecord(4 * curr, tempRec1, input);
                i1++;
                pool.getRecord(4 * (i1), tempRec1, temp);
                // A[curr] = temp[i1++];
            }
            else if (compareByteArray(tempRec1, tempRec2)) // Get smaller value
            {
                pool.writeRecord(4 * curr, tempRec1, input);
                i1++;
                pool.getRecord(4 * (i1), tempRec1, temp);
                // A[curr] = temp[i1++];
            }
            else
            {
                pool.writeRecord(4 * curr, tempRec2, input);
                i2++;
                pool.getRecord(4 * (i2), tempRec2, temp);
                // A[curr] = temp[i2++];
            }
        }
    }

    private boolean compareByteArray(byte[] obj, byte[] comp)
    {
        short objNum = ByteBuffer.wrap(obj).getShort();
        short compNum = ByteBuffer.wrap(comp).getShort();
        return objNum <= compNum;
    }
}
