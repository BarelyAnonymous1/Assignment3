import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import student.TestCase;

public class DoublyLinkedNodeTest extends TestCase {

    private DoublyLinkedNode node1;
    private DoublyLinkedNode node2;
    public void setUp() throws FileNotFoundException
    {
        RandomAccessFile file = new RandomAccessFile("buffertest.txt", "rw");
        node1 = new DoublyLinkedNode(new Buffer(0, file));
        node1 = new DoublyLinkedNode(new Buffer(0, file));
    }

    /**
     * tests the node class
     */
    public void testNodes()
    {
        node1.setNext(node2);
        assertEquals(node1.next, node2);
        node2.setPrev(node1);
        assertEquals(node1, node2.prev);
    }
    
}
