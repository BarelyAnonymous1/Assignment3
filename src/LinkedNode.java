/**
 * 
 * nodes to build a linked list of points
 * 
 * @author Jonathan DeFreeuw (jondef95) Preston Lattimer (platt)
 * @version 1
 */
public class LinkedNode
{
    /**
     * next node in the list
     */
    private LinkedNode next;
    /**
     * point data contained in the node
     */
    private Buffer     data;

    private int        index;

    /**
     * creates a node that contains data and no next node
     * 
     * @param newValue
     *            the value stored in the node
     */
    public LinkedNode(Buffer newValue)
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
    public void setNext(LinkedNode newNext)
    {
        next = newNext;
    }

    /**
     * get the next node
     * 
     * @return the node next to this one
     */
    public LinkedNode getNext()
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
     * returns the private KVPair stored in the node
     * 
     * @return the pair in the node
     */
    public Buffer getData()
    {
        return data;
    }
}
