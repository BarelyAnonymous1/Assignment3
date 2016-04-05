
public class RuntimeStats
{
    public static String file;
    public static int numBuffers;
    public static int foundInBuffer;
    public static int readDisk;
    public static int writeDisk;
    public static int startTime;
    public static int endTime;
    
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
    
    public String toString()
    {
        return null;
    }
}
