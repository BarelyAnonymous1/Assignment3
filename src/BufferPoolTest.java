import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Tests the methods in the BufferPool class to make sure that the methods work
 * appropriately
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 *
 */
public class BufferPoolTest extends TestCase
{

    BufferPool       buffpool;
    RandomAccessFile file;

    /**
     * sets up the tests
     * 
     * @throws FileNotFoundException
     */
    public void setUp() throws FileNotFoundException
    {
        buffpool = new BufferPool(3);
        file = new RandomAccessFile("buffertest.txt", "rw");
    }

    /**
     * tests the allocation of the buffer
     */
    public void testAllocateBuffer()
    {
        assertEquals(buffpool, buffpool.allocateBuffer(0, file));

    }
}
