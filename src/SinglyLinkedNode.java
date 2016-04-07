/**
 * 
 * nodes to build a linked list of points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class SinglyLinkedNode
{
    /**
     * next node in the list
     */
    private SinglyLinkedNode next;
    /**
     * point data contained in the node
     */
    private Buffer     data;

    /**
     * creates a node that contains data and no next node
     * 
     * @param newValue
     *            the value stored in the node
     */
    public SinglyLinkedNode(Buffer newValue)
    {
        data = newValue;
        next = null;
    }

    /**
     * sets the value of the next node
     * 
     * @param newNext
     *            the node next to this one
     */
    public void setNext(SinglyLinkedNode newNext)
    {
        next = newNext;
    }

    /**
     * get the next node
     * 
     * @return the node next to this one
     */
    public SinglyLinkedNode getNext()
    {
        return next;
    }
    


    /**
     * sets the value of the node
     * 
     * @param newData
     *            data to be stored in the node
     */
    public void setData(Buffer newData)
    {
        data = newData;
    }

    /**
     * returns the private Buffer stored in the node
     * 
     * @return the Buffer in the node
     */
    public Buffer getData()
    {
        return data;
    }
}
