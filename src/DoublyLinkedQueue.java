import java.io.File;

/**
 * basic linked list implementation; based on storing Points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DoublyLinkedQueue
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

        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    public void enqueue(Buffer buffer)
    {
        DoublyLinkedNode newNode = new DoublyLinkedNode(buffer);
        tail.getPrev();
        newNode.setPrev(tail.getPrev());
        tail.setPrev(newNode);
        newNode.setNext(tail);

        System.out.println("tried");
        System.out.println(buffer.getID());
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

    public Buffer remove(int blockID, File file)
    {
        DoublyLinkedNode curr = head.getNext();
        while (curr != tail)
        {
            if (curr.getData().getID() == blockID
                    && curr.getData().getFile() != null && curr.getData()
                            .getFile().toString().equals(file.toString()))
            {
                Buffer returnBuffer = curr.getData();
                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());
                size--;
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
