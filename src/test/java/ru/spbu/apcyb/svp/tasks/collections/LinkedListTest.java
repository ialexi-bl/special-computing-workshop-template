package ru.spbu.apcyb.svp.tasks.collections;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.spbu.apcyb.svp.tasks.collections.LinkedList;
import ru.spbu.apcyb.svp.tasks.collections.SimpleQueue;

/**
 * Тесты для задания 2.
 */
class LinkedListTest {
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "ResultOfMethodCallIgnored"})
    @Test
    void creatingList() {
        var list = new LinkedList<Integer>();

        Assertions.assertTrue(list.isEmpty(), "Newly created list must be empty");
        Assertions.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.get(0),
            "Newly created list may not contain any elements"
        );
    }

    @Test
    void add() {
        var list = new LinkedList<Integer>();

        list.add(123);

        Assertions.assertFalse(list.isEmpty(), "List must not be empty after adding elements");
        Assertions.assertEquals(1, list.size(), "Wrong list length");

        list.add(456);
        list.add(-10);

        Assertions.assertFalse(list.isEmpty(), "List must not be empty after adding elements");
        Assertions.assertEquals(3, list.size(), "Wrong list length");

        list.add(0, 100);
    }

    @Test
    void addIndex() {
        var list = new LinkedList<Integer>();

        list.add(0, 123);

        Assertions.assertFalse(list.isEmpty(), "List must not be empty after adding elements");
        Assertions.assertEquals(1, list.size(), "Wrong list length");

        list.add(0, 456);
        list.add(1, -10);

        Assertions.assertFalse(list.isEmpty(), "List must not be empty after adding elements");
        Assertions.assertEquals(3, list.size(), "Wrong list length");
        Assertions.assertEquals(456, list.get(0), "Wrong list element");
        Assertions.assertEquals(-10, list.get(1), "Wrong list element");
        Assertions.assertEquals(123, list.get(2), "Wrong list element");

        Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(4, 123),
                "Inserted to invalid index"
        );
        Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(-1, 123),
                "Inserted to invalid index"
        );
    }

    @Test
    void set() {
        var list = getListWithRange(3);

        list.set(0, -1);
        Assertions.assertEquals(3, list.size(), "List size should not have changed");
        Assertions.assertEquals(-1, list.get(0), "List item didn't change");

        list.set(2, -2);
        Assertions.assertEquals(3, list.size(), "List size should not have changed");
        Assertions.assertEquals(-1, list.get(0), "List item didn't change");

        Assertions.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.set(3, 0),
            "List shouldn't be able to set elements with invalid indices"
        );
        Assertions.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.set(-1, 0),
            "List shouldn't be able to set elements with invalid indices"
        );
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    void contains() {
        var list = getListWithRange(3);

        Assertions.assertTrue(list.contains(0), "List contains this element");
        Assertions.assertTrue(list.contains(2), "List contains this element");
        Assertions.assertFalse(list.contains(3), "List doesn't contain this element");
        Assertions.assertFalse(list.contains(new LinkedList<Integer>()),
            "List doesn't contain this element");
    }

    @Test
    void removeIndex() {
        var list = getListWithRange(3);

        list.remove(1);
        Assertions.assertEquals(2, list.size(), "Size should have decreased");
        Assertions.assertEquals(2, list.get(1), "Element should have changed");

        Assertions.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.remove(-1),
            "List shouldn't be able to delete element with this index"
        );
        Assertions.assertThrows(
            IndexOutOfBoundsException.class,
            () -> list.remove(2),
            "List shouldn't be able to delete element with this index"
        );

        list.remove(0);
        list.remove(0);
        //noinspection ConstantConditions
        Assertions.assertTrue(list.isEmpty(),
            "List should be empty when all elements are deleted");

        list.add(123);
        Assertions.assertEquals(123, list.get(0),
            "List should not break after removing all elements");
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    void removeObject() {
        var list = getListWithRange(3);

        list.remove((Object) 0);
        Assertions.assertEquals(2, list.size(), "Size should have decreased");
        Assertions.assertEquals(1, list.get(0), "Element should have changed");

        list.remove((Object) 0);
        Assertions.assertEquals(2, list.size(), "Size should not have changed");
        Assertions.assertEquals(1, list.get(0), "Element should not have changed");

        list.remove(new LinkedList<Integer>());
        Assertions.assertEquals(2, list.size(), "Size should not have changed");
        Assertions.assertEquals(1, list.get(0), "Element should not have changed");

        list.remove((Object) 1);
        list.remove((Object) 2);
        Assertions.assertTrue(list.isEmpty(),
            "List should be empty when all elements are deleted");

        list.add(123);
        Assertions.assertEquals(123, list.get(0),
            "List should not break after removing all elements");

    }

    @Test
    void clear() {
        var list = getListWithRange(3);

        list.clear();
        // noinspection ConstantConditions
        Assertions.assertEquals(0, list.size(), "Size should have decreased");
        Assertions.assertTrue(list.isEmpty(), "List should have become empty");

        list.add(123);
        Assertions.assertEquals(123, list.get(0),
            "List should not break after removing all elements");
    }

    private LinkedList<Integer> getListWithRange(int size) {
        var list = new LinkedList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    @Test
    public void containsAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().containsAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void addAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().addAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void removeAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().removeAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void retainAll() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().retainAll(new ArrayList<>()),
                "Not implemented"
        );
    }

    @Test
    public void iterator() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                new LinkedList<Integer>()::iterator,
                "Not implemented"
        );
    }

    @Test
    public void listIterator() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                new LinkedList<Integer>()::listIterator,
                "Not implemented"
        );
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().listIterator(123),
                "Not implemented"
        );
    }

    @Test
    public void subList() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().subList(0, 23),
                "Not implemented"
        );
    }

    @Test
    public void indexOf() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().indexOf(0),
                "Not implemented"
        );
    }

    @Test
    public void lastIndexOf() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().lastIndexOf(0),
                "Not implemented"
        );
    }

    @Test
    public void toArray() {
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                new LinkedList<Integer>()::toArray,
                "Not implemented"
        );
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> new LinkedList<Integer>().toArray(new Integer[2]),
                "Not implemented"
        );
    }
}