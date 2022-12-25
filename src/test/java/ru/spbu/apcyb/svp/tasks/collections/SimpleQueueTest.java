package ru.spbu.apcyb.svp.tasks.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@SuppressWarnings({"ResultOfMethodCallIgnored", "SuspiciousMethodCalls"})
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
        Assertions.assertEquals(1, queue.size(), "Wrong queue size");
        Assertions.assertEquals(123, queue.peek(), "Wrong queue element");
    }

    @Test
    void addMultiple() {
        var queue = new SimpleQueue<Integer>();

        queue.add(123);
        queue.add(234);
        queue.add(345);

        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after adding elements");
        Assertions.assertEquals(3, queue.size(), "Wrong queue size");
        Assertions.assertEquals(123, queue.peek(), "Wrong queue element");
    }

    @Test
    void offer() {
        var queue = new SimpleQueue<Integer>();

        queue.offer(123);

        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after adding elements");
        Assertions.assertEquals(1, queue.size(), "Wrong queue size");
        Assertions.assertEquals(123, queue.peek(), "Wrong queue element");
    }

    @Test
    void offerMultiple() {
        var queue = new SimpleQueue<Integer>();

        queue.offer(123);
        queue.offer(234);
        queue.offer(345);

        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after adding elements");
        Assertions.assertEquals(3, queue.size(), "Wrong queue size");
        Assertions.assertEquals(123, queue.peek(), "Wrong queue element");
    }

    @Test
    void poll() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(0, queue.poll(), "Wrong queue element");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after polling");

        Assertions.assertEquals(1, queue.poll(), "Wrong queue element");
        Assertions.assertTrue(queue.isEmpty(), "Queue must be empty after polling its last element");

        Assertions.assertNull(queue.poll(), "Peek must return null for empty queue");
    }

    @Test
    void peek() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(0, queue.peek(), "Wrong queue element");
        Assertions.assertEquals(0, queue.peek(), "Top element must not change after peeking");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after peeking");
    }

    @Test
    void peekEmpty() {
        Assertions.assertNull(new SimpleQueue<Integer>().peek(), "Peek must return null for empty queue");
    }

    @Test
    void element() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(0, queue.element(), "Wrong queue element");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after taking top element");

        Assertions.assertEquals(0, queue.element(), "Element should not have changed");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after element()");
    }

    @Test
    void elementEmpty() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                new SimpleQueue<Integer>()::element,
                "element() must throw exception for empty queue");
    }

    @Test
    void removeTop() {
        var queue = getQueueWithRange(2);

        Assertions.assertEquals(queue.remove(), 0, "Wrong queue element");
        Assertions.assertFalse(queue.isEmpty(), "Queue must not be empty after taking top element");

        Assertions.assertEquals(queue.remove(), 1, "Wrong queue element");
        Assertions.assertTrue(queue.isEmpty(), "Queue must be empty after removing last element");
    }

    @Test
    void removeEmpty() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                new SimpleQueue<Integer>()::remove,
                "remove() must throw exception for empty queue");
    }


    @Test
    void contains() {
        var queue = getQueueWithRange(3);

        Assertions.assertTrue(queue.contains(0), "Queue contains this element");
        Assertions.assertTrue(queue.contains(2), "Queue contains this element");
    }

    @Test
    void containsInvalid() {
        var queue = getQueueWithRange(3);

        Assertions.assertFalse(queue.contains(3), "Queue doesn't contain this element");
        Assertions.assertFalse(queue.contains(null), "Queue doesn't contain this element");
        Assertions.assertFalse(queue.contains(new SimpleQueue<Integer>()), "Queue doesn't contain this element");
    }

    @Test
    void removeObject() {
        var queue = getQueueWithRange(3);

        queue.remove(0);
        Assertions.assertEquals(2, queue.size(), "Size should have decreased");
        Assertions.assertEquals(1, queue.peek(), "Top element should not have changed");

        queue.remove(0);
        Assertions.assertEquals(2, queue.size(), "Size should not have changed");
        Assertions.assertEquals(1, queue.peek(), "Top element should not have changed");
    }

    @Test
    void removeObjectMultiple() {
        var queue = getQueueWithRange(3);

        queue.remove(0);
        queue.remove(2);
        queue.remove(1);

        Assertions.assertTrue(queue.isEmpty(), "List should be empty when all elements are deleted");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    void removeObjectInvalid() {
        var queue = getQueueWithRange(3);

        queue.remove(new LinkedList<Integer>());
        Assertions.assertEquals(3, queue.size(), "Size should not have changed");
        Assertions.assertEquals(0, queue.peek(), "Top element should not have changed");
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
        var queue = new SimpleQueue<Integer>();
        for (int i = 0; i < size; i++) {
            queue.add(i);
        }
        return queue;
    }

    @Test
    public void containsAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new SimpleQueue<Integer>().containsAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void addAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new SimpleQueue<Integer>().addAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void removeAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new SimpleQueue<Integer>().removeAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void retainAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new SimpleQueue<Integer>().retainAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void iterator() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                new SimpleQueue<Integer>()::iterator,
                "Not implemented"
        );
    }

    @Test
    public void toArray() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                new SimpleQueue<Integer>()::toArray,
                "Not implemented"
        );
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new SimpleQueue<Integer>().toArray(new Integer[2]),
                "Not implemented"
        );
    }
}
