import java.io.*;
import java.util.Arrays;

/**
 * BufferPool that is implemented with a modified linked queue to use the Least
 * Recently Used model for flushing. Blocks in the buffers are 4096 bytes, with
 * records being 4 bytes. Blocks and records are byte arrays.
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class BufferPool
{
    /**
     * standard size of the Buffer; number of bytes in the Buffer
     */
    public static int BUFFER_SIZE = 4096;
    /**
     * standard size of the records; number of bytes for each record
     */
    public static int RECORD_SIZE = 4;
    /**
     * modified linked queue used to implement the insertion and cycling of
     * Buffers
     */
    private LRUQueue  pool;

    /**
     * creates the linked queue with a maximum number of nodes allowed in the
     * list
     * 
     * @param startMax
     *            the max number of blocks the bufferpool can hold (per project
     *            spec)
     */
    public BufferPool(int startMax)
    {
        pool = new LRUQueue(startMax);
    }

    /**
     * returns the buffer that is relevant for the given record
     * 
     * @param recordPos
     * @param searchFile
     * @return the Buffer that contains the record we are searching for
     */
    public Buffer allocateBuffer(int recordPos,
            RandomAccessFile searchFile)
    {
        // cycle the BufferPool
        pool.makeMostRecent(recordPos, searchFile);
        // if a new Buffer was moved to MRU, change the Buffer values
        if (pool.getMRU().getFile() != searchFile
                || pool.getMRU().getID() != recordPos / BUFFER_SIZE)
            pool.getMRU().reset(recordPos / BufferPool.BUFFER_SIZE,
                    searchFile);
        return pool.getMRU(); // return the Buffer that was just used
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