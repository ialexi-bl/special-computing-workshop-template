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
        Assertions.assertThrows(NoSuchElementException.class, queue::element, "Newly created queue may not contain top element");
    }

    @Test
    void add() {
        var queue = new SimpleQueue<Integer>();

        queue.add(123);

        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after adding elements");
        Assertions.assertEquals(queue.size(), 1, "Wrong queue size");
        Assertions.assertEquals(queue.peek(), 123, "Wrong queue element");

        queue.add(234);
        queue.add(345);

        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after adding elements");
        Assertions.assertEquals(queue.size(), 3, "Wrong queue size");
    }

    @Test
    void poll() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(queue.poll(), 1, "Wrong queue element");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after polling");

        Assertions.assertEquals(queue.poll(), 0, "Wrong queue element");
        Assertions.assertTrue(queue.isEmpty(), "Queue must be empty after polling its last element");

        Assertions.assertNull(queue.poll(), "Peek must return null for empty queue");
    }

    @Test
    void peek() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(queue.peek(), 1, "Wrong queue element");
        Assertions.assertEquals(queue.peek(), 1, "Top element must not change after peeking");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after peeking");

        Assertions.assertNull(queue.peek(), "Peek must return null for empty queue");
    }

    @Test
    void element() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(queue.element(), 1, "Wrong queue element");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after taking top element");

        Assertions.assertEquals(queue.poll(), 0, "Wrong queue element");
        Assertions.assertTrue(queue.isEmpty(), "Queue must be empty after polling its last element");

        Assertions.assertThrows(NoSuchElementException.class, queue::element, "element() must throw exception for empty queue");
    }

    @Test
    void contains() {
        var queue = getQueueWithRange(3);

        Assertions.assertTrue(queue.contains(0), "Queue contains this element");
        Assertions.assertTrue(queue.contains(2), "Queue contains this element");
        Assertions.assertFalse(queue.contains(3), "Queue doesn't contain this element");
        Assertions.assertFalse(queue.contains(new SimpleQueue<Integer>()), "Queue doesn't contain this element");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    void removeObject() {
        var queue = getQueueWithRange(3);

        queue.remove(0);
        Assertions.assertEquals(2, queue.size(), "Size should have decreased");
        Assertions.assertEquals(2, queue.peek(), "Top element should not have changed");

        queue.remove(0);
        Assertions.assertEquals(2, queue.size(), "Size should not have changed");
        Assertions.assertEquals(2, queue.peek(), "Top element should not have changed");

        queue.remove(new LinkedList<Integer>());
        Assertions.assertEquals(2, queue.size(), "Size should not have changed");
        Assertions.assertEquals(2, queue.peek(), "Top element should not have changed");

        queue.remove(2);
        Assertions.assertEquals(1, queue.size(), "Size should have changed");
        Assertions.assertEquals(1, queue.peek(), "Top element should have changed");

        queue.remove(1);
        Assertions.assertTrue(queue.isEmpty(), "List should be empty when all elements are deleted");

        queue.add(123);
        Assertions.assertEquals(123, queue.peek(), "Queue should not break after removing all elements");
    }

    @Test
    void clear() {
        var queue = getQueueWithRange(3);

        queue.clear();
        // noinspection ConstantConditions
        Assertions.assertEquals(0, queue.size(), "Size should have decreased");
        Assertions.assertTrue(queue.isEmpty(), "Queue should have become empty");

        queue.add(123);
        Assertions.assertEquals(123, queue.peek(), "Queue should not break after removing all elements");
    }

    private SimpleQueue<Integer> getQueueWithRange(int size) {
        var list = new SimpleQueue<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

}
