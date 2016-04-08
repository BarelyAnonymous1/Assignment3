import java.io.*;
import java.nio.ByteBuffer;

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
    public void setUp() throws IOException
    {
        buffpool = new BufferPool(2);
        file = new RandomAccessFile("buffertest.txt", "rw");
        byte[] test = new byte[4096];
        byte[] test2 = new byte[4096];
        byte[] test3 = new byte[4096];

        for (int i = 0; i < 4096; i++)
            test[i] = "a".getBytes()[0];
        for (int j = 0; j < 4096; j++)
            test2[j] = "b".getBytes()[0];
        for (int k = 0; k < 4096; k++)
            test2[k] = "c".getBytes()[0];
        file.write(test);
        file.write(test2);
        Mergesort.fileSize = 12285;
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
        assertTrue(buffpool.getSize() == 2);
    }

    public void testGetRecord() throws IOException
    {
        assertTrue(buffpool.allocateBuffer(0, file).getFile() == file);
        byte[] sample = new byte[4];
        byte[] compare = "aaaa".getBytes();
        buffpool.getRecord(0, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        assertTrue(buffpool.allocateBuffer(4096, file).getFile() == file);
        compare = "bbbb".getBytes();
        buffpool.getRecord(4096, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        compare = "aaaa".getBytes();
        buffpool.getRecord(36, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
        assertTrue(buffpool.getSize() == 2);
    }

    public void testWriteRecord() throws IOException
    {
        buffpool.allocateBuffer(0, file);
        buffpool.allocateBuffer(4096, file);
        byte[] sample = new byte[4];
        byte[] compare = "cccc".getBytes();
        buffpool.getRecord(8192, sample, file);
        assertTrue(ByteBuffer.wrap(sample).compareTo(ByteBuffer.wrap(
                compare)) == 0);
    }
}
