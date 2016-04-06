import java.io.*;

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
        RuntimeStats.newCalls++;
        tail = new DoublyLinkedNode(null);
        RuntimeStats.newCalls++;

        head.setNext(tail);
        tail.setPrev(head);
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
    public void enqueue(DoublyLinkedNode newNode)
    {
        tail.getPrev().setNext(newNode);
        newNode.setPrev(tail.getPrev());
        tail.setPrev(newNode);
        newNode.setNext(tail);

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
    public DoublyLinkedNode dequeue()
    {
        if (size == 0)
            return null;
        else
        {
            DoublyLinkedNode temp = head.getNext();
            head.setNext(temp.getNext());
            temp.getNext().setPrev(head);
            temp.setNext(null);
            temp.setPrev(null);
            size--;
            return temp;
        }
    }
    /**
     * removes from the middle of the queue and relinks the next and previous
     * @param blockID the block of the node
     * @param file the file to look for the block in
     * @return the node with the block removed
     */
    public DoublyLinkedNode remove(int blockID, RandomAccessFile file)
    {
        DoublyLinkedNode curr = head.getPrev();
        while (curr != tail)
        {
            if (curr.getData().getID() == blockID
                    && curr.getData().getFile() != null && curr.getData()
                            .getFile().toString().equals(file.toString()))
            {
                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());
                curr.setPrev(null);
                curr.setNext(null);
                size--;
                return curr;
            }
            curr = curr.getPrev();
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
    
    public void setMRUBuffer(Buffer newBuffer)
    {
        head.getNext().setData(newBuffer);
    }

}
