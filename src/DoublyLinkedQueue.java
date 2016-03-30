import java.io.File;

/**
 * basic linked list implementation; based on storing Points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DoublyLinkedQueue<E>
{
    /**
     * pointer to the first node in the list
     */
    private DoublyLinkedNode head;
    /**
     * pointer to the end of the list
     */
    private DoublyLinkedNode tail;

    /**
     * number of nodes in the list
     */
    private int              size;

    /**
     * default constructor for the LinkedList
     */
    public DoublyLinkedQueue()
    {
        head = new DoublyLinkedNode(null);
        tail = new DoublyLinkedNode(null);
        size = 0;
    }

    /**
     * creates a LinkedList based on a single node
     * 
     * @param startBuffer
     *            the data that will start the list
     */
    public DoublyLinkedQueue(Buffer startBuffer)
    {
        head.setNext(new DoublyLinkedNode(startBuffer));
        head.getNext().setNext(tail);
        head.getNext().setPrev(head);
        tail.setPrev(head.getNext());
        size = 1;
    }

    public void enqueue(Buffer buffer)
    {
        DoublyLinkedNode newNode = new DoublyLinkedNode(buffer);
        if (head.getNext() == tail)
        {
            head.setNext(newNode);
            newNode.setNext(tail);
            newNode.setPrev(head);
            tail.setPrev(newNode);
        }
        else
        {
            newNode.setNext(tail);
            newNode.setPrev(tail.getPrev());
            tail.setPrev(newNode);
            newNode.getPrev().setNext(newNode);
        }
        size++;
    }

    public Buffer dequeue()
    {
        if (head.getNext() == tail)
            return null;
        else
        {
            DoublyLinkedNode temp = head.getNext();
            head.setNext(temp.getNext());
            temp.getNext().setPrev(head);
            size--;
            return temp.getData();
        }
    }

    public Buffer removeFromMiddle(int blockID, File file)
    {
        DoublyLinkedNode curr = head;
        while (curr != tail)
        {
            if (curr.getData().getID() == blockID && curr.getData()
                    .getFile().toString().equals(file.toString()))
            {
                Buffer returnBuffer = curr.getData();
                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());
                return returnBuffer;
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
    public DoublyLinkedNode getHead()
    {
        return head;
    }

    public DoublyLinkedNode getTail()
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
