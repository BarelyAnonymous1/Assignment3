import java.util.Arrays;
import java.io.File;

public class Buffer
{

    private byte[] block;
    private int    index;
    private File   file;

    /**
     * constructor for the Buffer class
     * This class will do the file I/O to interface with the BufferPool
     * @param pageIndex
     * @param startFile
     */
    public Buffer(int pageIndex, File startFile)
    {
        block = null;
        index = pageIndex;
        file = startFile;
    }

    public int getID()
    {
        return index;
    }

    /**
     * grabs a block from the file
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
        return Arrays.copyOfRange(block, pos % 4096, 4);
    }
    
    public void flush()
    {
        
    }
}