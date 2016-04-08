import java.io.*;
import org.junit.Test;

import student.TestCase;

public class DoublyLinkedQueueTest extends TestCase {

    private DoublyLinkedNode node1;
    private DoublyLinkedNode node2;
    private RandomAccessFile file; 
    private DoublyLinkedQueue list;
    public void setUp() throws IOException
    {
        file = new RandomAccessFile("buffertest.txt", "rw");
        node1 = new DoublyLinkedNode(new Buffer(0, file));
        node2 = new DoublyLinkedNode(new Buffer(0, file));
        list = new DoublyLinkedQueue();
    }

    @Test
    public void testEnqueue() {
        list.enqueue(node1);
        assertEquals(1, list.getSize());
        list.enqueue(node2);
        assertEquals(2, list.getSize());
    }

    @Test
    public void testDequeue() {
        assertNull(list.dequeue());
        list.enqueue(node1);
        list.enqueue(node2);
        assertEquals(list.dequeue(), node1);
        assertEquals(list.dequeue(), node2);
    }

    @Test
    public void testRemove() {
        list.remove(0, file);
        list.enqueue(node1);
        list.enqueue(node2);
        assertNotSame(node2, list.remove(4, file));
        assertNull(list.remove(0, null));
        assertEquals(node2, list.remove(0, file));
    }

}