import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class Merger
{
    /**
     * two temp byte arrays to prevent multi-initialization 
     * and minimize reads of the same record locally
     */
    private byte[] tempRec1;
    private byte[] tempRec2;

    public Merger()
    {
        tempRec1 = new byte[BufferPool.recordSize];
        tempRec2 = new byte[BufferPool.recordSize];
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
            pool.getRecord(BufferPool.recordSize * i, tempRec1, input);
            pool.writeRecord(BufferPool.recordSize * i, tempRec1, temp);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
        //preallocate tempRecs
        pool.getRecord(BufferPool.recordSize * (i1), tempRec1, temp);
        pool.getRecord(BufferPool.recordSize * (i2), tempRec2, temp);
        for (int curr = left; curr <= right; curr++)
        {
            if (i1 == mid + 1) // Left sublist exhausted
            {
                pool.writeRecord(BufferPool.recordSize * curr, tempRec2, input);
                i2++;
                pool.getRecord(BufferPool.recordSize * (i2), tempRec2, temp);
                // A[curr] = temp[i2++];

            }
            else if (i2 > right) // Right sublist exhausted
            {
                pool.writeRecord(BufferPool.recordSize * curr, tempRec1, input);
                i1++;
                pool.getRecord(BufferPool.recordSize * (i1), tempRec1, temp);
                // A[curr] = temp[i1++];
            }
            else if (compareByteArray(tempRec1, tempRec2)) // Get smaller value
            {
                pool.writeRecord(BufferPool.recordSize * curr, tempRec1, input);
                i1++;
                pool.getRecord(BufferPool.recordSize * (i1), tempRec1, temp);
                // A[curr] = temp[i1++];
            }
            else
            {
                pool.writeRecord(BufferPool.recordSize * curr, tempRec2, input);
                i2++;
                pool.getRecord(BufferPool.recordSize * (i2), tempRec2, temp);
                // A[curr] = temp[i2++];
            }
        }
    }

    /**
     * helper method to compare byte arrays (works for all machines)
     * @param obj left comparison
     * @param comp right comparison
     * @return a boolean for whether the array is less or equal to another
     */
    private boolean compareByteArray(byte[] obj, byte[] comp)
    {
        short objNum = ByteBuffer.wrap(obj).getShort();
        short compNum = ByteBuffer.wrap(comp).getShort();
        return objNum <= compNum;
    }
}
