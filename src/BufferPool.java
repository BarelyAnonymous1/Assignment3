import java.io.*;
import java.util.Arrays;

public class BufferPool
{
    public static int    BUFFER_SIZE = 4096;
    public static int    RECORD_SIZE = 4;
    private LRUQueue     pool;

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
        pool = new LRUQueue(startMax);
//        for (int i = 0; i < startMax; i++)
//        {
//            // the ID for each filler Buffer is so the Buffer pool knows to do
//            // nothing
//            // with it when it is removed
//            pool.makeMostRecent((-4096) * (i + 1), null);
//        }
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
        pool.makeMostRecent(recordPos, searchFile);
        if (pool.getMRU().getFile() != searchFile
                || pool.getMRU().getID() != recordPos / BUFFER_SIZE)
            pool.getMRU().reset(recordPos, searchFile);
        return pool.getMRU();
    } 


    public void writeRecord(int recordPos, byte[] record,
            RandomAccessFile file)
    {
        // recordpos % buffersize is the position within a single block
        allocateBuffer(recordPos, file).setBlock(record,
                recordPos % BufferPool.BUFFER_SIZE);
    }

    public void getRecord(int recordPos, byte[] record,
            RandomAccessFile file)
    {
        allocateBuffer(recordPos, file).getRecord(record,
                recordPos % BufferPool.BUFFER_SIZE);
    }

    /**
     * removes everything from the bufferPool starts with the least recently
     * used block
     */
    public void flushPool(String statName)
    {
        Buffer bufferToFlush = pool.removeLRU();
        while (bufferToFlush != null)
        {
            bufferToFlush.flush();
            bufferToFlush = pool.removeLRU();
        }
        RandomAccessFile statFile;
        try
        {
            RuntimeStats.endTime = System.currentTimeMillis();
            statFile = new RandomAccessFile(statName, "rw");
            statFile.seek(statFile.length());
            statFile.write(RuntimeStats.toStaticString().getBytes());
            statFile.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}