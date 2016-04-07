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
        Buffer buffer = new Buffer(5, null);
        Buffer buffer2 = new Buffer(10, null);
        System.out.println(list.toString());
    }
    
}
