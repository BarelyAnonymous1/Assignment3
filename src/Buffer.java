import java.util.Arrays;
import java.io.File;

public class Buffer
{

    private byte[] block;
    private int position;
    private int    index;
    private int BUFFER_SIZE = 4096;
    private int RECORD_SIZE = 4;

    private File   file;

    /**
     * constructor for the Buffer class This class will do the file I/O to
     * interface with the BufferPool
     * 
     * @param pageIndex
     * @param startFile
     */
    public Buffer(int position, File startFile)
    {
        block = null;
        index = position / BUFFER_SIZE;
        file = startFile;
    }

    public int getID()
    {
        return index;
    }
    
    public void storeBlock()
    {
        
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

    public void setBlock(byte[] newPage)
    {
        block = newPage;
    }

    public File getFile()
    {
        return file;
    }

    public byte[] getRecord(int pos)
    {
        return Arrays.copyOfRange(block, pos % BUFFER_SIZE, RECORD_SIZE);
    }

    public void flush()
    {
        System.out.println("Just flushed: " + index);
    }
}