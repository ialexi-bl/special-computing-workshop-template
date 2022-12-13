package ru.spbu.apcyb.svp.tasks.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Standard queue.
 */
public class SimpleQueue<T> implements java.util.Queue<T> {

    LinkedList<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public T remove() {
        if (list.size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.remove(0);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean offer(T t) {
        return add(t);
    }

    @Override
    public T poll() {
        if (list.size() == 0) {
            return null;
        }
        return list.remove(0);
    }

    @Override
    public T peek() {
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public T element() {
        if (list.size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.get(0);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }
}
