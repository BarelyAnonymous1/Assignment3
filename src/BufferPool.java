import java.io.*;
import java.util.Arrays;

public class BufferPool
{
    public static int    BUFFER_SIZE = 4096;
    public static int    RECORD_SIZE = 4;
    private LRUQueue     pool;
    private int          maxBuffers;
    public static byte[] TEMP_REC;

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
    public Buffer allocateBuffer(int recordPos,
            RandomAccessFile searchFile)
    {
        Buffer toFlush = pool.makeMostRecent(recordPos, searchFile);
        if (toFlush != null)
            toFlush.flush();
        if ((searchFile != null && pool.getMRU().getFile() != null) && pool.getMRU().getFile().toString() != searchFile.toString()
                || pool.getMRU().getID() != recordPos / BUFFER_SIZE)
            pool.getMRU().reset(recordPos, searchFile);
        return pool.getMRU();
    }

    public void writeRecord(int recordPos, byte[] record,
            RandomAccessFile file)
    {
        Buffer buffer = allocateBuffer(recordPos, file);
        // recordpos % buffersize is the position within a single block
        buffer.setBlock(record, recordPos % BufferPool.BUFFER_SIZE);
    }

    public byte[] getRecord(int recordPos, RandomAccessFile file)
    {

        Buffer found = allocateBuffer(recordPos, file);
        return Arrays.copyOfRange(found.getBlock(),
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
