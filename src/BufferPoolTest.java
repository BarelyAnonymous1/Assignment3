import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

public class BufferPoolTest extends TestCase {

    BufferPool buffpool;
    RandomAccessFile file;
    /**
     * sets up the tests
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
