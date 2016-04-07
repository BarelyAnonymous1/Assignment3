import java.io.RandomAccessFile;

/**
 * basic linked list implementation; based on storing Points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class SinglyLinkedQueue
{
    /**
     * pointer to the first node in the list
     */
    private SinglyLinkedNode head;

    /**
     * number of nodes in the list
     */
    private int              size;

    /**
     * default constructor for the LinkedList
     */
    public SinglyLinkedQueue()
    {
        head = new SinglyLinkedNode(null);
        RuntimeStats.newCalls++;
        head.setNext(null);
        size = 0;
    }

    /**
     * adds a new node into the linked queue
     * This node is inserted into the front of the queue
     *     -------------
     * ->  x
     *     -------------
     * @param newNode the node to be inserted
     */
    public void enqueue(SinglyLinkedNode newNode)
    {
        newNode.setNext(head.getNext());
        head.setNext(newNode);
        size++;
    }

    /**
     * pulls the last added node from the queue
     * this node removed from the queue
     *     -------------
     *     x-x-x-x-x-x   x ->
     *     -------------
     * @return
     */
    public SinglyLinkedNode dequeue()
    {
        if (size == 0)
            return null;
        else
        {
            SinglyLinkedNode temp = head.getNext();
            while (temp.getNext().getNext() != null)
            {
                temp = temp.getNext();
            }
            SinglyLinkedNode temp2 = temp.getNext();
            temp.setNext(null);
            size--;
            return temp2;
        }
    }
    /**
     * removes from the middle of the queue and relinks the next and previous
     * @param blockID the block of the node
     * @param file the file to look for the block in
     * @return the node with the block removed
     */
    public SinglyLinkedNode remove(int blockID, RandomAccessFile file)
    {
        SinglyLinkedNode curr = head.getNext();
        while (curr.getNext() != null)
        {
            if (curr.getNext().getData().getID() == blockID
                    && curr.getNext().getData().getFile() != null && curr.getNext().getData()
                            .getFile().toString().equals(file.toString()))
            {
                SinglyLinkedNode temp = curr.getNext();
                curr.setNext(curr.getNext().getNext());
                curr.setNext(null);
                size--;
                return temp;
            }
            curr = curr.getNext();
        }
        return null;
    }

    /**
     * get the pointer to the head of the list
     * 
     * @return the head of the list
     */
    public SinglyLinkedNode getHead()
    {
        return head;
    }

    public String toString()
    {
        String str = "";
        SinglyLinkedNode temp = head;
        while (temp.getNext() != null)
        {
            str += temp.getNext().getData();
            str += "\n";
            temp = temp.getNext();
        }
        return str;
    }
    /**
     * get the size of the list; size should not include duplicates
     * 
     * @return size of the list, no duplicates
     */
    public int getSize()
    {
        return size;
    }
    
    public SinglyLinkedNode getEnd()
    {
        SinglyLinkedNode temp = head;
        while (temp.getNext() != null)
        {
            temp = temp.getNext();
        }
        return temp;
    }
    
    public void setMRUBuffer(Buffer newBuffer)
    {
        head.getNext().setData(newBuffer);
    }

}
