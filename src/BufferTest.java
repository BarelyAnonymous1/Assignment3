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

    public void setUpClass() throws IOException
    {
        file = new RandomAccessFile("testin.txt", "rw");
        test = new byte[4096];
        for (int i = 0; i < 4096; i++)
        {
            test[i] = "a".getBytes()[0];
        }
        file.write(test);
    }

    public void testStoreBlock() throws IOException
    {
        Mergesort.FILE_SIZE = 100000;
        buffer = new Buffer(0, file);
        assertEquals(ByteBuffer.wrap(buffer.getBlock())
                .compareTo(ByteBuffer.wrap(test)), 0);
    }

}
