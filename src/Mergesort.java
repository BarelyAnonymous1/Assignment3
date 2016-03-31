import java.io.*;
/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author { your name here }
 * @version { put something here }
 */
public class Mergesort {

	/**
	 * The entry point of the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	    RandomAccessFile input;
	    try{
	        input = new RandomAccessFile(args[0], "rw");
	    }
	    catch (FileNotFoundException e)
	    {
	        System.out.println("File '" + args[0] + "' was not found");
	    }
	    
		BufferPool bufpool = new BufferPool(3);
		bufpool.newBuffer(1, null);
		bufpool.newBuffer(2, null);
		bufpool.newBuffer(3, null);
		bufpool.newBuffer(4, null);
	}
}