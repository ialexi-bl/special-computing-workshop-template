package ru.spbu.apcyb.svp.tasks.collections;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleQueueTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void creatingQueue() {
        var queue = new SimpleQueue<Integer>();

        Assertions.assertTrue(queue.isEmpty(), "Newly created queue should be empty");
        Assertions.assertNull(queue.peek(), "Newly created queue should not contain top element");
        Assertions.assertNull(queue.poll(), "Newly created queue should not contain top element");
        Assertions.assertThrows(
            NoSuchElementException.class,
            queue::element,
            "Newly created queue may not contain top element"
        );
    }

    
}
