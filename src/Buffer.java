import java.util.Arrays;
import java.io.*;

public class Buffer
{

    private byte[] block;
    private int    index;
    private int blockSize;
    private RandomAccessFile file;

    public Buffer(byte[] newPage, int pageIndex, int newBlockSize)
    {
        block = newPage;
        index = pageIndex;
        blockSize = newBlockSize;
    }
    /**
     * which block in the file this is (1, 2, 3... multiples of 4096)
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    
//    public byte[] getBlock()
//    {
//        return block;
//    }
//
//    public void setBlock(byte[] newPage)
//    {
//        block = newPage;
//    }

    public byte[] getRecord(int pos)
    {
        return Arrays.copyOfRange(block, pos % blockSize, 4);
    }
    
   
    public void flush()
    {
        
    }
}
