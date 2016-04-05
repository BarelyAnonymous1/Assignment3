
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
        DoublyLinkedNode foundNode = list.remove(newBuffer.getID(), newBuffer.getFile());
        if (foundNode == null)
        {
            list.enqueue(new DoublyLinkedNode(newBuffer));
            if (list.getSize() > MAX_SIZE)
            {
                return list.dequeue().getData();
            }
            else
                return null;
        }
        else
        {
            list.enqueue(foundNode);
            return null;
        }
    }
    
    public Buffer removeLRU()
    {
        return list.dequeue().getData(); 
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
