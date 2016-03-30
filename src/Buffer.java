import java.util.Arrays;
import java.io.*;

public class Buffer
{

    private byte[] block;
    private int    index;
    private int blockSize;

    public Buffer(byte[] newPage, int pageIndex, int newBlockSize)
    {
        block = newPage;
        index = pageIndex;
        blockSize = newBlockSize;
    }

    public int getIndex()
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

    public byte[] getRecord(int pos)
    {
        return Arrays.copyOfRange(block, pos % blockSize, 4);
    }
}
