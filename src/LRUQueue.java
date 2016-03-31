
public class LRUQueue
{
    private final int MAX_SIZE;
    private DoublyLinkedQueue list;
    
    public LRUQueue(int max)
    {
        MAX_SIZE = max;
        list = new DoublyLinkedQueue();
    }
    
    /**
     * will add a buffer to the list if the ID and file name dont already exist in a buffer in the list. if the list was shifted or 
     * @param newBuffer
     * @return
     */
    public Buffer addOrShift(Buffer newBuffer)
    {
        Buffer existingBuffer = list.remove(newBuffer.getID(), newBuffer.getFile());
        if (existingBuffer == null)
        {
            list.enqueue(newBuffer);
            if (list.getSize() > MAX_SIZE)
                return list.dequeue();
            else
                return null;
        }
        else
        {
            list.enqueue(existingBuffer);
            return null;
        }
    }
    
    public Buffer removeLRU()
    {
        return list.dequeue();        
    }
    
    public int getSize()
    {
        return list.getSize();
    }
    
    public DoublyLinkedQueue getLRUQueue()
    {
        return list;
    }
}
