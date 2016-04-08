
public abstract class RuntimeStats
{
    public static String file = "";
    public static int    numBuffers = 0;
    public static int    foundInBuffer = 0;
    public static int    readDisk = 0;
    public static int    writeDisk = 0;
    public static long   startTime = 0;
    public static long   endTime = 0;

  

    public String toStaticString()
    {
        long total = endTime - startTime;
        return ("Filename: " + file + "\nNumber of Buffers: " + numBuffers
                + "\nCache Hits: " + foundInBuffer + "\nReads: " + readDisk
                + "\nWrites: " + writeDisk + "\nTotal Time: " + total
                + " milliseconds\n\n");
    }
}
