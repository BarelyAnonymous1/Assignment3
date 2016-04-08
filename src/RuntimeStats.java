/**
 * Class that is used to keep count of a set of stats on the BufferPool-based
 * Mergesort.
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class RuntimeStats
{
    /**
     * name of the file
     */
    public static String file          = "";
    /**
     * max number of buffers
     */
    public static int    numBuffers    = 0;
    /**
     * number of times a record was found in the bufferpool
     */
    public static int    foundInBuffer = 0;
    /**
     * number of times a new block was read from the disk
     */
    public static int    readDisk      = 0;
    /**
     * number of times a block was written to disk
     */
    public static int    writeDisk     = 0;
    /**
     * starting time for the sort
     */
    public static long   startTime     = 0;
    /**
     * ending time for the sort
     */
    public static long   endTime       = 0;

    /**
     * creates a string representation of the statistics found in sorting a file
     * 
     * @return a string representation of the stats
     */
    public static String toStaticString()
    {
        long total = endTime - startTime;
        return ("Filename: " + file + "\nNumber of Buffers: " + numBuffers
                + "\nCache Hits: " + foundInBuffer + "\nReads: " + readDisk
                + "\nWrites: " + writeDisk + "\nTotal Time: " + total
                + " milliseconds\n\n");
    }
}
