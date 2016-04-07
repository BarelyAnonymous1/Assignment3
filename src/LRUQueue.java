import java.io.*;

public class LRUQueue
{
    private final int         MAX_SIZE;
    private SinglyLinkedQueue list;

    public LRUQueue(int max)
    {
        MAX_SIZE = max;
        list = new SinglyLinkedQueue();
        RuntimeStats.newCalls++;
    }

    /**
     * will add a buffer to the list if the ID and file name dont already exist
     * in a buffer in the list. if the list was shifted or
     * 
     * @param newBuffer
     * @return
     */
    public Buffer addOrPromote(Buffer newBuffer)
    {
        SinglyLinkedNode foundNode = list.remove(newBuffer.getID(),
                newBuffer.getFile());
        if (foundNode == null)
        {
            list.enqueue(new SinglyLinkedNode(newBuffer));
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

    public void makeMostRecent(int recordPos,
            RandomAccessFile searchFile)
    {
        SinglyLinkedNode foundNode = list
                .remove(recordPos / BufferPool.BUFFER_SIZE, searchFile);
        if (foundNode == null)
        {
            if (list.getSize() < MAX_SIZE)
            {
                list.enqueue(new SinglyLinkedNode(
                        (new Buffer(recordPos, searchFile))));
                RuntimeStats.newCalls++;
                RuntimeStats.newCalls++;
            }
            else
            {
                SinglyLinkedNode lruNode = list.dequeue();
                list.enqueue(lruNode);
                lruNode.getData().flush();
            }
        }
        else
        {
            list.enqueue(foundNode);
            RuntimeStats.foundInBuffer++;
        }
    }

    public Buffer removeLRU()
    {
        SinglyLinkedNode found = list.dequeue();
        if (found != null)
            return found.getData();
        else
            return null;
    }

    /**
     * tostring
     * @return string
     */
    public String toString()
    {
        return list.toString();
    }
    public int getSize()
    {
        return list.getSize();
    }

    public SinglyLinkedQueue getLRUQueue()
    {
        return list;
    }

    public Buffer getMRU()
    {
        return list.getHead().getData();
    }
}
