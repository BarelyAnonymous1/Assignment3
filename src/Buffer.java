import java.io.*;

public class Buffer
{
    private byte[]           block;
    private int              index;
    private boolean          dirtyBit;
    private int              size;
    private int              furthestByte;

    private RandomAccessFile file;

    /**
     * constructor for the Buffer class This class will do the file I/O to
     * interface with the BufferPool
     * 
     * @param pageIndex
     * @param startFile
     */
    public Buffer(int startID, RandomAccessFile startFile)
    {
        block = new byte[BufferPool.BUFFER_SIZE];
        index = startID / BufferPool.BUFFER_SIZE;
        file = startFile;
        dirtyBit = false;
        furthestByte = 0;
        storeBlock();
    }

    public void reset(int startPosition, RandomAccessFile startFile)
    {
        index = startPosition / BufferPool.BUFFER_SIZE;
        file = startFile;
        dirtyBit = false;
        furthestByte = 0;
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

    public void getRecord(byte[] record, int pos)
    {
        record[0] = block[pos];
        record[1] = block[pos + 1];
        record[2] = block[pos + 2];
        record[3] = block[pos + 3];
    }

    public void setBlock(byte[] newPage, int recordNum)
    {
        dirtyBit = true;
        // System.arraycopy(newPage, 0, block, recordNum,
        // BufferPool.RECORD_SIZE);
        block[recordNum] = newPage[0];
        block[recordNum + 1] = newPage[1];
        block[recordNum + 2] = newPage[2];
        block[recordNum + 3] = newPage[3];
        furthestByte = recordNum + 4;
        // System.arraycopy(record, 0, block, recordNum,
        // BufferPool.RECORD_SIZE);
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
                file.write(block, 0, furthestByte);
                RuntimeStats.writeDisk++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}