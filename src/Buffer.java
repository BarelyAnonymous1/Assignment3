import java.util.Arrays;
import java.io.*;
import java.io.File;

public class Buffer
{

    private byte[]           block;
    private int              position;
    private int              index;
    private boolean          hasBlock;

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
        position = startPosition;
        index = position / BufferPool.BUFFER_SIZE;
        file = startFile;
        hasBlock = false;
    }

    public int getID()
    {
        return index;
    }

    public void storeBlock()
    {
        if (!hasBlock)
        {
            try
            {
                file.seek(index);
                file.read(block);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            hasBlock = true;
        }
    }

    /**
     * grabs a block from the file
     * 
     * @return the block from the file
     */
    public byte[] getBlock()
    {
        storeBlock();
        return block;
    }

    public void setBlock(byte[] newPage)
    {
        block = newPage;
        System.arraycopy(newPage, 0, block, 0, BufferPool.RECORD_SIZE);
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
            file.seek(index * BufferPool.BUFFER_SIZE);
            file.write(block);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Just flushed: " + index);
    }
}