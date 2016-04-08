import java.io.*;

public class LRUQueue
{
    private final int         MAX_SIZE;
    private DoublyLinkedQueue list;

    public LRUQueue(int max)
    {
        MAX_SIZE = max;
        list = new DoublyLinkedQueue();
    }

    public void makeMostRecent(int recordPos, RandomAccessFile searchFile)
    {
        DoublyLinkedNode foundNode = list
                .remove(recordPos / BufferPool.BUFFER_SIZE, searchFile);
        if (foundNode == null)
        {
            if (list.getSize() < MAX_SIZE)
            {
                list.enqueue(new DoublyLinkedNode((new Buffer(
                        recordPos / BufferPool.BUFFER_SIZE, searchFile))));
            }
            else
            {
                DoublyLinkedNode lruNode = list.dequeue();
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
        DoublyLinkedNode found = list.dequeue();
        if (found != null)
            return found.getData();
        else
            return null;
    }

    /**
     * tostring
     * 
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

    public DoublyLinkedQueue getLRUQueue()
    {
        return list;
    }

    public Buffer getMRU()
    {
        return list.tail.prev.getData();
    }
}
