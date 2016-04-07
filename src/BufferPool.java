import java.io.*;
import java.util.Arrays;

public class BufferPool
{
    public static int    BUFFER_SIZE = 4096;
    public static int    RECORD_SIZE = 4;
    private LRUQueue     pool;
    private int          maxBuffers;
    public static byte[] TEMP_REC;
    private Buffer allocatedBuffer;

    /**
     * initializes the bufferpool with -1, -2, -3, ... for whatever the startMax
     * is. Uses LRUQueue implementation to create the bufferpool
     * 
     * @param startMax
     *            the max number of blocks the bufferpool can hold (per project
     *            spec)
     */
    public BufferPool(int startMax)
    {
        maxBuffers = startMax;
        pool = new LRUQueue(startMax);
        TEMP_REC = new byte[RECORD_SIZE];
        allocatedBuffer = null;
        for (int i = 0; i < maxBuffers; i++)
        {
            // the ID for each filler Buffer is so the Buffer pool knows to do
            // nothing
            // with it when it is removed
            pool.makeMostRecent((-4096) * (i + 1), null);
        }
    }

    /**
     * returns the buffer that is relevant for the given record
     * 
     * @param recordPos
     * @param searchFile
     * @return
     */
    public void allocateBuffer(int recordPos,
            RandomAccessFile searchFile)
    {
        Buffer toFlush = pool.makeMostRecent(recordPos, searchFile);
        if (toFlush != null)
            toFlush.flush();
        allocatedBuffer = pool.getMRU();
        if (allocatedBuffer.getFile() != searchFile
                || allocatedBuffer.getID() != recordPos / BUFFER_SIZE)
            allocatedBuffer.reset(recordPos, searchFile);
    }

    public void writeRecord(int recordPos,// byte[] record,
            RandomAccessFile file)
    {
        allocateBuffer(recordPos, file);
        // recordpos % buffersize is the position within a single block
        allocatedBuffer.setBlock(recordPos % BufferPool.BUFFER_SIZE);
    }

    public void getRecord(int recordPos, RandomAccessFile file)
    {

        allocateBuffer(recordPos, file);
        TEMP_REC = Arrays.copyOfRange(allocatedBuffer.getBlock(),
                recordPos % BUFFER_SIZE,
                recordPos % BUFFER_SIZE + RECORD_SIZE);
    }

    /**
     * removes everything from the bufferPool starts with the least recently
     * used block
     */
    public void flushPool()
    {
        Buffer bufferToFlush = pool.removeLRU();
        while (bufferToFlush != null)
        {
            bufferToFlush.flush();
            bufferToFlush = pool.removeLRU();
        }
    }
}
