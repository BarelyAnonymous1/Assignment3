import java.util.Arrays;
import java.io.*;
import java.io.File;

public class Buffer
{

    private byte[]           block;
    private int              index;
    private boolean          hasBlock;
    private boolean          dirtyBit;

    private RandomAccessFile file;

    /**
     * constructor for the Buffer class This class will do the file I/O to
     * interface with the BufferPool
     * 
     * @param pageIndex
     * @param startFile
     */
    public Buffer(int startPosition, RandomAccessFile startFile)
    {
        block = new byte[BufferPool.BUFFER_SIZE];
        RuntimeStats.newCalls++;
        index = startPosition / BufferPool.BUFFER_SIZE;
        file = startFile;
        storeBlock();
    }

    public void reset(int startPosition, RandomAccessFile startFile)
    {
        index = startPosition / BufferPool.BUFFER_SIZE;
        file = startFile;
        storeBlock();
    }

    public int getID()
    {
        return index;
    }

    public void storeBlock()
    {
        try
        {
            if (file != null)
            {
                file.seek(index * BufferPool.BUFFER_SIZE);
                file.read(block);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * grabs a block from the file
     * 
     * @return the block from the file
     */
    public byte[] getBlock()
    {
        return block;
    }

    public void setBlock(byte[] newPage, int recordNum)
    {
        System.arraycopy(newPage, 0, block, recordNum,
                BufferPool.RECORD_SIZE);
        System.out.println(block[recordNum + 1]);

    }

    public RandomAccessFile getFile()
    {
        return file;
    }

    public void flush()
    {
        if (file == null)
            return;
        try
        {
            // if (dirtyBit)
            // {
            file.seek(index * BufferPool.BUFFER_SIZE);
            file.write(block);
            // }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}