import java.io.*;

public class BufferPool
{
    public static int BUFFER_SIZE = 4096;
    public static int RECORD_SIZE = 4;
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
        for (int i = 0; i < maxBuffers; i++)
        {
            // the ID for each filler Buffer is so the Buffer pool knows to do
            // nothing
            // with it when it is removed
            pool.addOrShift(new Buffer((-4096) * (i + 1), null));
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
    public Buffer newBuffer(int searchID, RandomAccessFile searchFile)
    {
        // look for a block in the file
        Buffer foundBuffer = getBuffer(searchID, searchFile);
        if (foundBuffer == null)
        {
            foundBuffer = new Buffer(searchID, searchFile);
            Buffer bufferToFlush = pool.addOrShift(foundBuffer);
            if (bufferToFlush != null)
                bufferToFlush.flush();
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
    public Buffer getBuffer(int searchID, RandomAccessFile searchFile)
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

    public byte[] getRecord(RandomAccessFile file, int recordPos)
    {
        byte[] returnArray = new byte[BufferPool.RECORD_SIZE];
        Buffer found = newBuffer(recordPos / BufferPool.BUFFER_SIZE, file);

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
