
public class RuntimeStats
{
    private static String file;
    private static int    numBuffers;
    public static int     foundInBuffer;
    public static int     readDisk;
    public static int     writeDisk;
    public static long    startTime;
    public static long    endTime;
    
    public RuntimeStats(String filename, int numberOfBuffers)
    {
        file = filename;
        numBuffers = numberOfBuffers;
        foundInBuffer = 0;
        readDisk = 0;
        writeDisk = 0;
        startTime = 0;
        endTime = 0;
    }

    public static String toStaticString()
    {
        long total = endTime - startTime;
        return ("Filename: " + file + "\nNumber of Buffers: " + numBuffers
                + "\nCache Hits: " + foundInBuffer
                + "\nReads: " + readDisk + "\nWrites: "
                + writeDisk + "\nTotal Time: " + total + " milliseconds\n");
    }
}
