import java.io.*;
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
            // the ID for each filler Buffer is so the Buffer pool knows to do nothing 
            // with it when it is removed
            pool.addOrShift(new Buffer(null, (-1)*(i+1), null));
        }
    }
    
    public Buffer newBuffer(int searchID, File searchFile)
    {
        Buffer returnBuffer = getBuffer(searchID, searchFile);
    }
    
    public Buffer getBuffer(int searchID, File searchFile)
    {
        DoublyLinkedNode existNode = pool.getLRUQueue().remove(searchID, searchFile);
        if (existNode != null)
        {
            pool.getLRUQueue().enqueue(existNode);
            return existNode.getData();
        }
        else
            return null;
    }

}
