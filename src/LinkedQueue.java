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
    public LinkedList()
    {
        head = null;
        size = 0;
    }

    /**
     * creates a LinkedList based on a single node
     * 
     * @param startBuffer
     *            the data that will start the list
     */
    public LinkedList(Buffer startBuffer)
    {
        head = new LinkedNode(startBuffer);
        size = 1;
    }

    /**
     * removes the head from the list, and replaces the head with the next node
     * 
     * @return the data stored in the head of the node
     */
    public Point removeHead()
    {
        if (head.getNext() != null)
        {
            Point temp = head.getData();
            head = head.getNext();
            return temp;
        }
        else
        {
            Point output = head.getData();
            head = null;
            return output;
        }
    }

    /**
     */
    public void insert(Buffer newBuffer)
    {
        
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
