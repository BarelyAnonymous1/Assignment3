import java.io.*;

public class Buffer
{
    private byte[]           block;
    private int              index;
    private boolean          dirtyBit;
    private int              size;

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
        dirtyBit = false;
        storeBlock();
    }

    public void reset(int startPosition, RandomAccessFile startFile)
    {
        index = startPosition / BufferPool.BUFFER_SIZE;
        file = startFile;
        dirtyBit = false;
        RuntimeStats.numReset++;
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
                if ((index + 1)
                        * BufferPool.BUFFER_SIZE > Mergesort.FILE_SIZE)
                {
                    file.read(block, 0, Mergesort.FILE_SIZE
                            - (index * BufferPool.BUFFER_SIZE));
                    size = (Mergesort.FILE_SIZE
                            - (index * BufferPool.BUFFER_SIZE));
                }
                else
                {
                    file.read(block);
                    size = BufferPool.BUFFER_SIZE;
                }
                RuntimeStats.readDisk++;
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
        dirtyBit = true;
        block[recordNum] = newPage[0];
        block[recordNum + 1] = newPage[1];
        block[recordNum + 2] = newPage[2];
        block[recordNum + 3] = newPage[3];

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
            if (dirtyBit)
            {
                file.seek(index * BufferPool.BUFFER_SIZE);
                file.write(block);
                RuntimeStats.writeDisk += 4096; // 75411456
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}