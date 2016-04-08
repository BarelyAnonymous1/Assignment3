import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * Tests the methods for the Buffer to make sure it properly reads and writes
 * from a file
 * 
 * @author Preston Lattimer (platt) Jonathan DeFreeuw (jondef95)
 *
 */
public class BufferTest extends TestCase
{

    private RandomAccessFile file;
    private Buffer           buffer;
    private byte[]           test;
    private byte[]           test2;

    public void setUp() throws IOException
    {
        file = new RandomAccessFile("testin.txt", "rw");
        test = new byte[4096];
        test2 = new byte[4096];

        for (int i = 0; i < 4096; i++)
        {
            test[i] = "a".getBytes()[0];
        }
        for (int j = 0; j < 4096; j++)
        {
            test2[j] = "b".getBytes()[0];
        }
        file.write(test);
        file.write(test2);
    }

    public void testStoreBlock() throws IOException
    {
        Mergesort.FILE_SIZE = 8192;
        buffer = new Buffer(0, file);
        assertEquals(ByteBuffer.wrap(buffer.getBlock())
                .compareTo(ByteBuffer.wrap(test)), 0);
    }
    
    public void testReset() throws IOException
    {
        Mergesort.FILE_SIZE = 8192;
        buffer = new Buffer(0, file);
        assertTrue(ByteBuffer.wrap(buffer.getBlock())
                .compareTo(ByteBuffer.wrap(test)) == 0);
        buffer.reset(1, file);
        assertTrue(ByteBuffer.wrap(buffer.getBlock())
                .compareTo(ByteBuffer.wrap(test2)) == 0);
    }
    
    public void testRecord() throws IOException
    {
        Mergesort.FILE_SIZE = 8192;
        buffer = new Buffer(0, file);
    }

}
