import org.junit.Before;
import org.junit.Test;

import student.TestCase;

public class SinglyLinkedQueueTest extends TestCase {

    SinglyLinkedQueue list;
    public void setUp()
    {
        list = new SinglyLinkedQueue();
    }

    public void testInsert()
    {
        Buffer buffer = new Buffer(3, null);
        Buffer buffer2 = new Buffer(10, null);
        list.enqueue(new SinglyLinkedNode(buffer));
        System.out.println(list.toString());
        list.enqueue(new SinglyLinkedNode(buffer));
        System.out.println(list.toString());
        list.enqueue(new SinglyLinkedNode(buffer2));
        System.out.println(list.toString());
        list.dequeue();
        System.out.println(list.toString());
    }
    
}
