/**
 * basic linked list implementation; based on storing Points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class LinkedQueue
{
    /**
     * pointer to the first node in the list
     */
    private LinkedNode head;
    /**
     * pointer to the end of the list
     */
    private LinkedNode tail;

    /**
     * number of nodes in the list
     */
    private int        size;
    


    /**
     * default constructor for the LinkedList
     */
    public LinkedQueue()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * creates a LinkedList based on a single node
     * 
     * @param startBuffer
     *            the data that will start the list
     */
    public LinkedQueue(Buffer startBuffer)
    {
        head = new LinkedNode(startBuffer);
        tail = head;
        size = 1;
    }
    
    public void enqueue(Buffer buffer)
    {
        LinkedNode newNode = new LinkedNode(buffer);
        newNode.setNext(tail);
        tail = newNode;
        if (head == null)
            head = tail;
        size++;
    }

    public Buffer dequeue()
    {
        if (head == null)
            return null;
        else if (tail == head)
        {
            Buffer temp = head.getData();
            head = null;
            tail = head;
            size = 0;
            return temp;
        }
        else
        {
            LinkedNode curr = tail;
            while (curr.getNext() != head)
                curr = curr.getNext();
            Buffer temp = head.getData();
            head = curr;
            head.setNext(null);
            size--;
            return temp;
        }
    }
    
    public void shiftList(Buffer shiftBuffer)
    {
        LinkedNode curr = tail;
        while (curr.getNext() != head)
        {
            if (shiftBuffer.getIndex() == curr.getData().getIndex())
            {
                
            }
            curr = curr.getNext();
        }
    }

    /**
     * get the pointer to the head of the list
     * 
     * @return the head of the list
     */
    public LinkedNode getHead()
    {
        return head;
    }
    
    public LinkedNode getTail()
    {
        return tail;
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

}
