package ru.spbu.apcyb.svp.tasks;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.spbu.apcyb.svp.tasks.collections.LinkedList;

/**
 * Тесты для задания 2.
 */
class LinkedListTest {

    private LinkedList<Integer> getListWithRange(int size) {
        var list = new LinkedList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "ResultOfMethodCallIgnored"})
    @Test
    void creatingList() {
        var list = new LinkedList<Integer>();

        Assertions.assertTrue(list.isEmpty(), "Newly created list must be empty");
        Assertions.assertThrows(
            NoSuchElementException.class,
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
        Assertions.assertEquals(123, list.get(0), "Wrong list contents");

        list.add(456);
        list.add(-10);

        Assertions.assertFalse(list.isEmpty(), "List must not be empty after adding elements");
        Assertions.assertEquals(3, list.size(), "Wrong list length");
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
            NoSuchElementException.class,
            () -> list.set(3, 0),
            "List shouldn't be able to set elements with invalid indices"
        );
        Assertions.assertThrows(
            NoSuchElementException.class,
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
            NoSuchElementException.class,
            () -> list.remove(-1),
            "List shouldn't be able to delete element with this index"
        );
        Assertions.assertThrows(
            NoSuchElementException.class,
            () -> list.remove(2),
            "List shouldn't be able to delete element with this index"
        );

        list.remove(0);
        list.remove(1);
        //noinspection ConstantConditions
        Assertions.assertFalse(list.isEmpty(),
            "List should be empty when all elements are deleted");

        list.add(123);
        Assertions.assertEquals(123, list.get(1),
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
        Assertions.assertFalse(list.isEmpty(),
            "List should be empty when all elements are deleted");

        list.add(123);
        Assertions.assertEquals(123, list.get(1),
            "List should not break after removing all elements");

    }

    @Test
    void clear() {
        var list = getListWithRange(3);

        list.clear();
        //noinspection ConstantConditions
        Assertions.assertEquals(0, list.size(), "Size should have decreased");
        Assertions.assertTrue(list.isEmpty(), "List should have become empty");

        list.add(123);
        Assertions.assertEquals(123, list.get(1),
            "List should not break after removing all elements");
    }
}
