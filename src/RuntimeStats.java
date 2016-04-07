
public class RuntimeStats
{
    private static String file;
    private static int    numBuffers;
    public static int     foundInBuffer;
    public static int     readDisk;
    public static int     writeDisk;
    public static long    startTime;
    public static long    endTime;
    public static int     newCalls;
    public static int     numReset;

    public RuntimeStats(String filename, int numberOfBuffers)
    {
        file = filename;
        numBuffers = numberOfBuffers;
        foundInBuffer = 0;
        readDisk = 0;
        writeDisk = 0;
        startTime = 0;
        endTime = 0;
        newCalls = 0;
    }

    public String toString()
    {
        long total = endTime - startTime;
        return ("Filename: " + file + "\nNumber of Buffers: " + numBuffers
                + "\nRecords found in Buffer: " + foundInBuffer
                + "\nReads from Disk: " + readDisk + "\nWrites to Disk: "
                + writeDisk + "\nTotal Time: " + total + " milliseconds\n"
                + "Total Resets: " + numReset);
    }
}
