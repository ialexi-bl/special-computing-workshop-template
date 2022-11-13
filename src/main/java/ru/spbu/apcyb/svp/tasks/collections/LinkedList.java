package ru.spbu.apcyb.svp.tasks.collections;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

/**
 * Связный список.
 */
public class LinkedList<T> implements List<T> {

    private class Node {

        T value;
        @Nullable
        Node next = null;
        @Nullable
        Node previous = null;

        public Node(T value, @Nullable Node previous, @Nullable Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

    }

    private int size = 0;
    @Nullable
    private Node first;
    @Nullable
    private Node last;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node current = first; current != null; current = current.next) {
            if (o.equals(current)) {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    private Node getNodeAt(int index) {
        if (index <= 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " out of bounds for length " + size);
        }

        Node current = first;
        int i = 0;
        while (current != null && i < index) {
            current = current.next;
            i++;
        }

        if (current == null) {
            throw new IllegalStateException("Invalid list state");
        }

        return current;
    }

    @Override
    public T get(int index) {
        return getNodeAt(index).value;
    }

    @Override
    public T set(int index, T element) {
        getNodeAt(index).value = element;
        return element;
    }

    @Override
    public boolean add(T t) {
        if (last == null) {
            first = last = new Node(t, null, null);
        } else {
            last = last.next = new Node(t, last, null);
        }
        size++;
        return true;
    }


    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size || first == null) {
            add(element);
        } else {
            Node node = getNodeAt(index);
            if (node.previous == null) {
                first = first.previous = new Node(element, null, first);
            } else {
                node.previous = node.previous.next = new Node(element, node.previous, node);
            }
            size++;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (first == null || last == null) {
            return false;
        }

        for (Node current = first; current != null; current = current.next) {
            if (o.equals(current)) {
                if (size == 1) {
                    first = last = null;
                } else if (current.previous == null) {
                    first = first.next;
                } else if (current.next == null) {
                    last = last.previous;
                } else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public T remove(int index) {
        Node node = getNodeAt(index);

        if (node.previous == null && node.next == null) {
            first = last = null;
        } else if (node.previous == null) {
            first = node.next;
            first.previous = null;
        } else if (node.next == null) {
            last = node.previous;
            last.next = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
        return node.value;
    }

    @Override
    public void clear() {
        size = 0;
        last = first = null;
    }


    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> iterator() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
    }

    @Override
    public <T1> T1[] toArray(@Nonnull T1[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(int index, @Nonnull Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

}
