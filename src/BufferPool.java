import java.io.*;
import java.util.Arrays;

public class BufferPool
{
    public static int BUFFER_SIZE = 4096;
    public static int RECORD_SIZE = 4;
    private static byte[] TEMP_RECORD;
    private LRUQueue  pool;
    private int       maxBuffers;

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
        TEMP_RECORD = new byte[RECORD_SIZE];
        for (int i = 0; i < maxBuffers; i++)
        {
            // the ID for each filler Buffer is so the Buffer pool knows to do
            // nothing
            // with it when it is removed
            pool.makeMostRecent((-4096) * (i + 1), null);
        }
    }

    /**
     * adds a new block to the bufferPool from the file
     * 
     * @param searchID
     *            the block to be added
     * @param searchFile
     *            the file to add the block from
     * @return the block if it is there, if not: null
     */
    public Buffer newBuffer(int recordPos, RandomAccessFile searchFile)
    {
        // look for a block in the file
        Buffer foundBuffer = getBuffer(recordPos / BufferPool.BUFFER_SIZE,
                searchFile);
        if (foundBuffer == null)
        {
            foundBuffer = new Buffer(recordPos, searchFile);
            RuntimeStats.newCalls++;
            Buffer bufferToFlush = pool.addOrPromote(foundBuffer);
            if (bufferToFlush != null)
                bufferToFlush.flush();
        }
        return foundBuffer;
    }

    public Buffer allocateBuffer(int recordPos,
            RandomAccessFile searchFile)
    {
        Buffer toFlush = pool.makeMostRecent(recordPos, searchFile);
        if (toFlush != null)
            toFlush.flush();
        if (pool.getMRU().getID() != recordPos / BUFFER_SIZE
                || pool.getMRU().getFile() != searchFile)
            pool.getMRU().reset(recordPos,  searchFile);
        return pool.getMRU();
    }

    /**
     * Looks for a block in the file
     * 
     * @param searchID
     *            the index of the block to be searched for
     * @param searchFile
     *            the file to search for the block in
     * @return the block if found
     */
    private Buffer getBuffer(int searchID, RandomAccessFile searchFile)
    {
        DoublyLinkedNode existNode = pool.getLRUQueue().remove(searchID,
                searchFile);
        if (existNode != null)
        {
            pool.getLRUQueue().enqueue(existNode);
            return existNode.getData();
        }
        else
            return null;
    }

    public void writeRecord(int recordPos, byte[] record,
            RandomAccessFile file)
    {
        Buffer buffer = allocateBuffer(recordPos, file);
        // recordpos % buffersize is the position within a single block
        buffer.setBlock(record, recordPos % BufferPool.BUFFER_SIZE);
    }

    public byte[] getRecordOld(int recordPos, RandomAccessFile file)
    {
        // byte[] returnArray = new byte[BufferPool.RECORD_SIZE];
        // RuntimeStats.newCalls++;
        Buffer found = newBuffer(recordPos, file);
        byte[] returnArray = new byte[RECORD_SIZE];
        System.arraycopy(found.getBlock(),
                recordPos % BufferPool.BUFFER_SIZE, returnArray, 0,
                BufferPool.RECORD_SIZE);
        return returnArray;
    }

    public byte[] getRecord(int recordPos, RandomAccessFile file)
    {
//        byte[] returnArray = new byte[BufferPool.RECORD_SIZE];
//        RuntimeStats.newCalls++;
        Buffer found = allocateBuffer(recordPos, file);
//        return Arrays.copyOfRange(found.getBlock(),
//                recordPos % BUFFER_SIZE,
//                recordPos % BUFFER_SIZE + RECORD_SIZE);
        System.arraycopy(found.getBlock(), recordPos % 4096, TEMP_RECORD, 0, 4);
        return TEMP_RECORD;
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

    /**
     * getter for the max size
     * 
     * @return the maximum number of buffers
     */
    public int getMaxSize()
    {
        return maxBuffers;
    }

}
