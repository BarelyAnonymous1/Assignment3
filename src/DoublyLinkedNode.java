/**
 * 
 * nodes to build a linked list of points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class DoublyLinkedNode<E>
{
    /**
     * next node in the list
     */
    private DoublyLinkedNode<E> next;
    private DoublyLinkedNode<E> prev;
    /**
     * point data contained in the node
     */
    private E     data;

    /**
     * creates a node that contains data and no next node
     * 
     * @param newValue
     *            the value stored in the node
     */
    public DoublyLinkedNode(E newValue)
    {
        data = newValue;
        prev = null;
        next = null;
    }

    /**
     * sets the value of the next node
     * 
     * @param newNext
     *            the node next to this one
     */
    public void setNext(DoublyLinkedNode<E> newNext)
    {
        next = newNext;
    }

    /**
     * get the next node
     * 
     * @return the node next to this one
     */
    public DoublyLinkedNode<E> getNext()
    {
        return next;
    }
    
    /**
     * sets the value of the next node
     * 
     * @param newPrev
     *            the node prev to this one
     */
    public void setPrev(DoublyLinkedNode<E> newPrev)
    {
        next = newPrev;
    }

    /**
     * get the next node
     * 
     * @return the node next to this one
     */
    public DoublyLinkedNode<E> getPrev()
    {
        return prev;
    }

    /**
     * sets the value of the node
     * 
     * @param newData
     *            data to be stored in the node
     */
    public void setData(E newData)
    {
        data = newData;
    }

    /**
     * returns the private KVPair stored in the node
     * 
     * @return the pair in the node
     */
    public E getData()
    {
        return data;
    }
}
