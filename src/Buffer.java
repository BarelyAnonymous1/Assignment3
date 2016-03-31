import java.util.Arrays;
<<<<<<< HEAD
import java.io.*;
=======
import java.io.File;
>>>>>>> branch 'master' of https://github.com/BarelyAnonymous1/Assignment3.git

public class Buffer
{

    private byte[] block;
    private int    index;
<<<<<<< HEAD
    private int blockSize;
    private RandomAccessFile file;
=======
    private File   file;
>>>>>>> branch 'master' of https://github.com/BarelyAnonymous1/Assignment3.git

<<<<<<< HEAD
    public Buffer(byte[] newPage, int pageIndex, int newBlockSize)
=======
    public Buffer(int pageIndex, File startFile)
>>>>>>> branch 'master' of https://github.com/BarelyAnonymous1/Assignment3.git
    {
        block = null;
        index = pageIndex;
<<<<<<< HEAD
        blockSize = newBlockSize;
=======
        file = startFile;
>>>>>>> branch 'master' of https://github.com/BarelyAnonymous1/Assignment3.git
    }
<<<<<<< HEAD
    /**
     * which block in the file this is (1, 2, 3... multiples of 4096)
     * @return the index
     */
    public int getIndex()
=======

    public int getID()
>>>>>>> branch 'master' of https://github.com/BarelyAnonymous1/Assignment3.git
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

    public File getFile()
    {
        return file;
    }

    public byte[] getRecord(int pos)
    {
        return Arrays.copyOfRange(block, pos % blockSize, 4);
    }
    
   
    public void flush()
    {
        
    }
    
    public void flush()
    {
        
    }
}
