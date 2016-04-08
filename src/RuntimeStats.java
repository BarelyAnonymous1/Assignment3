/**
 * Class that is used to keep count of a set of stats on the BufferPool-based
 * Mergesort. Is abstract to there is no way to insantiate the class. 
 * 
 * @author Jonathan
 *
 */
public abstract class RuntimeStats
{
    /**
     * 
     */
    public static String file          = "";
    public static int    numBuffers    = 0;
    public static int    foundInBuffer = 0;
    public static int    readDisk      = 0;
    public static int    writeDisk     = 0;
    public static long   startTime     = 0;
    public static long   endTime       = 0;

    public static String toStaticString()
    {
        long total = endTime - startTime;
        return ("Filename: " + file + "\nNumber of Buffers: " + numBuffers
                + "\nCache Hits: " + foundInBuffer + "\nReads: " + readDisk
                + "\nWrites: " + writeDisk + "\nTotal Time: " + total
                + " milliseconds\n\n");
    }
}
