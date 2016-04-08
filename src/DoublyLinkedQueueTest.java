import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import org.junit.Test;

import student.TestCase;

public class DoublyLinkedQueueTest extends TestCase {

    private DoublyLinkedNode node1;
    private DoublyLinkedNode node2;
    DoublyLinkedQueue list;
    public void setUp() throws FileNotFoundException
    {
        RandomAccessFile file = new RandomAccessFile("buffertest.txt", "rw");
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
        fail("Not yet implemented");
    }

    @Test
    public void testRemove() {
        fail("Not yet implemented");
    }

}
