import java.io.*;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Tests the methods in the BufferPool class to make sure that the methods work
 * appropriately
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 * @version 1
 */
public class BufferPoolTest extends TestCase
{

    private BufferPool       buffpool;
    private RandomAccessFile file;

    /**
     * sets up the tests
     * 
     * @throws FileNotFoundException
     */
    public void setUp() throws FileNotFoundException
    {
        buffpool = new BufferPool(3);
        file = new RandomAccessFile("buffertest.txt", "rw");
        Mergesort.fileSize = 8192;
    }

    /**
     * tests the allocation of the buffer
     */
    public void testAllocateBuffer() throws IOException
    {
        assertTrue(buffpool.allocateBuffer(0, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(0, file).getID() == 0);
        assertTrue(buffpool.allocateBuffer(4096, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(4096, file).getID() == 1);
        assertTrue(buffpool.allocateBuffer(1, file).getFile() == file);
        assertTrue(buffpool.allocateBuffer(1, file).getID() == 0);
    }
}
