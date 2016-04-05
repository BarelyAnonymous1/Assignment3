
public class LRUQueue
{
    private final int         MAX_SIZE;
    private DoublyLinkedQueue list;

    public LRUQueue(int max)
    {
        MAX_SIZE = max;
        list = new DoublyLinkedQueue();
        RuntimeStats.newCalls++;
    }

    /**
     * will add a buffer to the list if the ID and file name dont already exist
     * in a buffer in the list. if the list was shifted or
     * 
     * @param newBuffer
     * @return
     */
    public Buffer addOrPromote2(Buffer newBuffer)
    {
        DoublyLinkedNode foundNode = list.remove(newBuffer.getID(),
                newBuffer.getFile());
        if (foundNode == null)
        {
            list.enqueue(new DoublyLinkedNode(newBuffer));
            RuntimeStats.newCalls++;
            if (list.getSize() > MAX_SIZE)
                return list.dequeue().getData();
            else
                return null;
        }
        else
        {
            list.enqueue(foundNode);
            return null;
        }
    }

    /**
     * will add a buffer to the list if the ID and file name dont already exist
     * in a buffer in the list. if the list was shifted or
     * 
     * @param newBuffer
     * @return
     */
    public Buffer addOrPromote1(Buffer newBuffer)
    {
        DoublyLinkedNode foundNode = list.remove(newBuffer.getID(),
                newBuffer.getFile());
        if (foundNode == null)
        {
            if (list.getSize() > MAX_SIZE - 1)
            {
                foundNode = list.dequeue();
                Buffer returnBuffer = foundNode.getData();
                foundNode.setData(newBuffer);
                list.enqueue(foundNode);
                return returnBuffer;
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
        DoublyLinkedNode found = list.dequeue();
        if (found != null)
            return found.getData();
        else
            return null;
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
