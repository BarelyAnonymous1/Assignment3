import java.util.Arrays;

public class Buffer
{

    private byte[] block;
    private int    index;

    public Buffer(byte[] newPage, int pageIndex)
    {
        block = newPage;
        index = pageIndex;
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

    public byte[] getRecord(int pos)
    {
        return Arrays.copyOfRange(block, pos % 4096, 4);
    }
}
