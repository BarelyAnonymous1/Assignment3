import java.io.*;

public class BufferPool
{
    public static int BUFFER_SIZE = 4096;
    public static int RECORD_SIZE = 4;
    private LRUQueue  pool;
    private int       maxBuffers;
    public static byte[] TEMP_RECORD;

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
            pool.addOrPromote(new Buffer((-4096) * (i + 1), null));
            RuntimeStats.newCalls++;
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
    public Buffer newBuffer1(int recordPos, RandomAccessFile searchFile)
    {
        // look for a block in the file
        Buffer foundBuffer = getBuffer(recordPos / BufferPool.BUFFER_SIZE, searchFile);
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
    
    public Buffer newBuffer(int recordPos, RandomAccessFile searchFile)
    {
        // look for a block in the file
        Buffer foundBuffer = getBuffer(recordPos / BufferPool.BUFFER_SIZE, searchFile);
        if (foundBuffer == null)
        {
            Buffer bufferToFlush = cycle();
            if (bufferToFlush != null)
            {
                Buffer buffer = pool.removeLRU();
                buffer.reset(recordPos, searchFile);
                pool.addOrPromote(buffer);
                bufferToFlush.flush();
            }
        }
        return foundBuffer;
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
        Buffer buffer = newBuffer(recordPos,
                file);
        // recordpos % buffersize is the position within a single block
        buffer.setBlock(record, recordPos % BufferPool.BUFFER_SIZE);
    }

    public byte[] getRecord(int recordPos, RandomAccessFile file)
    {
        byte[] returnArray = new byte[BufferPool.RECORD_SIZE];
        RuntimeStats.newCalls++;
        Buffer found = newBuffer(recordPos, file);
        System.arraycopy(found.getBlock(),
                recordPos % BufferPool.BUFFER_SIZE, returnArray, 0,
                BufferPool.RECORD_SIZE);

        return returnArray;
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
    
    private Buffer cycle()
    {
        if (pool.getSize() == maxBuffers)
        {
            return pool.getLRUQueue().getHead().getNext().getData();
        }
        return null;
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
