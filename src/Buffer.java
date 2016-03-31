import java.util.Arrays;
import java.io.File;

public class Buffer
{

    private byte[] block;
    private int    index;
    private File   file;

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
}
