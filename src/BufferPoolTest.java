import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

public class BufferPoolTest extends TestCase {

    BufferPool buffpool;
    RandomAccessFile file;
    /**
     * sets up the tests
     */
    public void setUp()
    {
        buffpool = new BufferPool(3);
        file = new RandomAccessFile("test.txt", "rw");
    }
    
    /**
     * tests the allocation of the buffer
     */
    public void testAllocateBuffer()
    {
        
    }
}
