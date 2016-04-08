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
        index = resetID;
        file = resetFile;
        dirtyBit = false; // makes sure that the new block won't be written if
                          // it hasn't been changed
        furthestByte = 0; // make it so if the dirtyBit doesn't work, the block
                          // won't get written at all
        storeBlock(); // get a new block
    }

    /**
     * retrieves the block from the file, based on the sizes in the BufferPool.
     * won't read beyond the end of the file if the block size extends beyond
     * the end of the file
     */
    public void storeBlock()
    {
        try
        {
            // go to the byte position at the start of the block
            file.seek(index * BufferPool.BUFFER_SIZE);
            if ((index + 1) * BufferPool.BUFFER_SIZE > Mergesort.FILE_SIZE)
            { // check if the block would extend beyond the file
              // read from the start of the block to the end of the file
                file.read(block, 0, Mergesort.FILE_SIZE
                        - (index * BufferPool.BUFFER_SIZE));
            }
            else
            {
                file.read(block); // read the block from the file
            }
            RuntimeStats.readDisk++; // increment the number of disk reads
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * returns which block is in this buffer
     * 
     * @return the index of the block, in chunks of BUFFER_SIZE determined in
     *         the BufferPool 0 is 0-4095, 1 is 4096-8191, etc
     */
    public int getID()
    {
        return index;
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

    /**
     * returns a reference to the file contained the buffer
     * 
     * @return the pointer to the file in the buffer
     */
    public RandomAccessFile getFile()
    {
        return file;
    }

    /**
     * stores the record from the block into a byte array of size 4
     * 
     * While we understand that the use of magic numbers is frowned upon, we
     * decided that for the interest of efficiency for this project and keeping
     * our sort times down, that the use of inline array copying would be more
     * beneficial than using methods such as arraycopy or copyOfRange. We also
     * tried using a loop that would run from 0 to the RECORD_SIZE, but it was
     * still significantly slower than using inline code
     * 
     * @param record
     * @param pos
     */
    public void getRecord(byte[] record, int pos)
    {
        record[0] = block[pos];
        record[1] = block[pos + 1];
        record[2] = block[pos + 2];
        record[3] = block[pos + 3];

    }

    /**
     * stores a record into a specific spot in the block
     * 
     * While we understand that the use of magic numbers is frowned upon, we
     * decided that for the interest of efficiency for this project and keeping
     * our sort times down, that the use of inline array copying would be more
     * beneficial than using methods such as arraycopy or copyOfRange. We also
     * tried using a loop that would run from 0 to the RECORD_SIZE, but it was
     * still significantly slower than using inline code
     * 
     * @param record
     * @param recordNum
     */
    public void setBlock(byte[] record, int recordNum)
    {
        dirtyBit = true;
        block[recordNum] = record[0];
        block[recordNum + 1] = record[1];
        block[recordNum + 2] = record[2];
        block[recordNum + 3] = record[3];

        if (furthestByte < recordNum + 4)
            furthestByte = recordNum + 4;
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