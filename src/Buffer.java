import java.io.*;

/**
 * Buffer class to implement a buffer pool. Contains a block for the byte array,
 * which has a max size determined by a constant in the buffer pool class. The
 * buffer reads from a file to find its block, the edits that block before
 * writing back to the file.
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 *
 */
public class Buffer
{
    /**
     * block to hold a set amount of bytes from the file
     */
    private byte[]           block;
    /**
     * determines where in the file the block came from 0 means the first 0-4095
     * bytes, 1 is 4096-8191, etc
     */
    private int              index;
    /**
     * determines if the block has been edited (new record has been placed)
     */
    private boolean          dirtyBit;
    /**
     * the index of the furthest record that has been edited in the block; helps
     * the buffer not write the entire block if it can help it; optimization
     * decision
     */
    private int              furthestByte;
    /**
     * the specific file that the block has been read from and will write to
     */
    private RandomAccessFile file;

    /**
     * constructor for the Buffer class This class will do the file I/O to
     * interface with the BufferPool
     * 
     * @param startID
     *            the initial index for the buffer, determines where in the file
     *            the first block will come from
     * @param startFile
     *            the initial file for the buffer, determines which file the
     *            first block will come from
     */
    public Buffer(int startID, RandomAccessFile startFile)
    {
        block = new byte[BufferPool.BUFFER_SIZE]; // create the array necessary
                                                  // for operation
        reset(startID, startFile);
    }

    /**
     * resets all of the fields in the buffer; used to recycle Buffers in the
     * Buffer Pool
     * 
     * @param resetID
     *            new index for the block of the file
     * @param resetFile
     *            new file for the Buffer to read data from
     */
    public void reset(int resetID, RandomAccessFile resetFile)
    {
        index = resetID / BufferPool.BUFFER_SIZE;
        file = resetFile;
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
            file.seek(index * BufferPool.BUFFER_SIZE);
            if ((index + 1) * BufferPool.BUFFER_SIZE > Mergesort.FILE_SIZE)
            {
                file.read(block, 0, Mergesort.FILE_SIZE
                        - (index * BufferPool.BUFFER_SIZE));
            }
            else
            {
                file.read(block);
            }
            RuntimeStats.readDisk++;
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

        if (furthestByte < recordNum + 4)
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