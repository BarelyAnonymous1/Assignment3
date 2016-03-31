
public class BufferPool
{
    private LRUQueue pool;
    private int BUFFER_SIZE = 4096;
    private int maxBuffers;
    
    public BufferPool(int startMax)
    {
        maxBuffers = startMax;
        pool = new LRUQueue(startMax);
        for (int i = 0; i < maxBuffers; i++)
        {
            pool.addOrShift(new Buffer(null, (-1)*(i+1), null));
        }
    }

}
