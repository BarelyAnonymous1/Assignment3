import java.io.*;

/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class Mergesort
{

    /**
     * The entry point of the application
     * 
     * @param args
     */
    public static void main(String[] args)
    {
//        RandomAccessFile input;
//        try
//        {
//            input = new RandomAccessFile(args[0], "rw");
//        }
//        catch (IOException e)
//        {
//            System.out.println("File '" + args[0] + "' was not found");
//        }
//        
//        int numBuffers = Integer.parseInt(args[1]);
//        RandomAccessFile stats;
//        try
//        {
//            stats = new RandomAccessFile(args[2], "rw");
//        }
//        catch(IOException e)
//        {
//            System.out.println("There was an error");
//        }
//        
//        if (numBuffers < 1)
//        {
//            System.out.println(
//                    "Please provide at least 1 buffer for the buffer pool");
//            return;
//        }

//        BufferPool sortPool = new BufferPool(numBuffers);
        BufferPool bufpool = new BufferPool(3);
        bufpool.newBuffer(1, null);
        bufpool.newBuffer(2, null);
        bufpool.newBuffer(3, null);
        bufpool.newBuffer(4, null);
    }
}