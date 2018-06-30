package com.chudakov.data_structures.list;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T>, Deque<T> {
  private int size = 0;
  private Node<T> first;
  private Node<T> last;

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
    return indexOf(o) != -1;
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }

  @Override
  public Iterator<T> descendingIterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T1> T1[] toArray(T1[] a) {
    if (a.length < size) {
      a = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
    }
    Object[] result = a;
    int i = 0;
    for (Node<T> node = first; node != null; node = node.next) {
      result[i++] = node.data;
    }
    return a;
  }

  @Override
  public void addFirst(T t) {
    Node<T> node = new Node<>(t);
  }

  @Override
  public void addLast(T t) {}

  @Override
  public boolean offerFirst(T t) {
    addFirst(t);
    return true;
  }

  @Override
  public boolean offerLast(T t) {
    add(t);
    return true;
  }

  @Override
  public T removeFirst() {
    if (first == null) {
      throw new NoSuchElementException();
    }
    return unlinkFirst();
  }

  @Override
  public T removeLast() {
    if (last == null) {
      throw new NoSuchElementException();
    }
    return unlinkLast();
  }

  @Override
  public T pollFirst() {
    return first == null ? null : unlinkFirst();
  }

  @Override
  public T pollLast() {
    return last == null ? null : unlinkLast();
  }

  @Override
  public T getFirst() {
    if (first == null) {
      throw new NoSuchElementException();
    }
    return first.data;
  }

  @Override
  public T getLast() {
    if (last == null) {
      throw new NoSuchElementException();
    }
    return last.data;
  }

  @Override
  public T peekFirst() {
    return first == null ? null : first.data;
  }

  @Override
  public T peekLast() {
    return last == null ? null : last.data;
  }

  @Override
  public boolean removeFirstOccurrence(Object o) {
    return false;
  }

  @Override
  public boolean removeLastOccurrence(Object o) {
    return false;
  }

  @Override
  public boolean add(T t) {
    if (first == null) {
      Node<T> node = new Node<>(t);
      linkFirst(node);
      last = first;
    } else {
      linkLast(new Node<>(t));
    }
    return true;
  }

  @Override
  public boolean offer(T t) {
    return add(t);
  }

  @Override
  public T remove() {
    return removeFirst();
  }

  @Override
  public T poll() {
    return null;
  }

  @Override
  public T element() {
    return null;
  }

  @Override
  public T peek() {
    return null;
  }

  @Override
  public void push(T t) {}

  @Override
  public T pop() {
    return null;
  }

  private void linkLast(Node<T> node) {
    node.prev = last;
    if (last != null) {
      last.next = node;
    }
    last = node;
    size++;
  }

  private T unlinkLast() {
    T data = last.data;
    last.data = null;
    if (last.prev != null) {
      last.prev.next = null;
    }
    last = last.prev;
    size--;
    return data;
  }

  private void linkFirst(Node<T> first) {
    first.next = this.first;
    if (this.first != null) {
      this.first.prev = first;
    }
    this.first = first;
    size++;
  }

  private T unlinkFirst() {
    T data = first.data;
    first.data = null;
    if (first.next != null) {
      first.next.prev = null;
    }
    first = first.next;
    size--;
    return data;
  }

  private void link(Node<T> prev, Node<T> node, Node<T> next) {
    node.prev = prev;
    node.next = next;
    if (prev != null) {
      prev.next = node;
    }
    if (next != null) {
      next.prev = node;
    }
    size++;
  }

  private T unlink(Node<T> node) {
    T data = node.data;
    node.data = null;
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    if (node.prev != null) {
      node.prev.next = node.next;
    }
    size--;
    return data;
  }

  private Node<T> search(Object o) {return null;}

  @Override
  public boolean remove(Object o) {return false;}

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {}

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public T set(int index, T element) {
    return null;
  }

  @Override
  public void add(int index, T element) {}

  @Override
  public T remove(int index) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public ListIterator<T> listIterator() {
    return null;
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return null;
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return null;
  }

  private class Node<T> {
    T data;
    Node<T> next;
    Node<T> prev;

    public Node(T data) {
      this.data = data;
    }
  }
}
