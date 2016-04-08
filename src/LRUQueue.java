import java.io.*;

/**
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 *
 */
public class LRUQueue
{
    /**
     * the maximum size of the list
     */
    private final int         MAX_SIZE;
    /**
     * a private list in doubly linked implementation
     */
    private DoublyLinkedQueue list;

    public LRUQueue(int max)
    {
        MAX_SIZE = max;
        list = new DoublyLinkedQueue();
    }

    /**
     * searches the current buffer for a record. if found, adds to the list
     * if not, creates a new buffer for the correct record
     * @param recordPos the position to be searched
     * @param searchFile the file to search
     */
    public void makeMostRecent(int recordPos, RandomAccessFile searchFile)
    {
        DoublyLinkedNode foundNode = list
                .remove(recordPos / BufferPool.bufferSize, searchFile);
        if (foundNode == null)
        {
            if (list.getSize() < MAX_SIZE)
            {
                list.enqueue(new DoublyLinkedNode((new Buffer(
                        recordPos / BufferPool.bufferSize, searchFile))));
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

    /**
     * removes the least recently used node
     * @return the buffer removed
     */
    public Buffer removeLRU()
    {
        DoublyLinkedNode found = list.dequeue();
        if (found != null)
            return found.getData();
        else
            return null;
    }

    /**
     * getter for the size
     * @return the size
     */
    public int getSize()
    {
        return list.getSize();
    }

    /**
     * getter for MRU
     * @return most recently used node
     */
    public Buffer getMRU()
    {
        return list.tail.prev.getData();
    }
}
