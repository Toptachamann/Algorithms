package com.chudakov.algorithms.data_structures;

public class MyPriorityQueue<V extends Comparable<V>> {
  private static final int DEFAULT_CAPACITY = 20;
  Object[] queue;
  int capacity;
  int size;

  public MyPriorityQueue() {
    this(DEFAULT_CAPACITY);
  }

  public MyPriorityQueue(int capacity) {
    this.capacity = capacity;
    this.queue = new Object[capacity];
  }

  public void put(V value) {
    if (size == capacity) {
      resize();
    }
    queue[size++] = value;
    moveUp(size - 1);
  }

  private void moveUp(int pos) {
    if (pos > 0) {
      V value = (V) queue[pos];
      int parentPos = (pos - 1) >> 1;
      V parentValue = (V) queue[parentPos];
      if (value.compareTo(parentValue) > 0) {
        queue[pos] = parentValue;
        queue[parentPos] = value;
        moveUp(parentPos);
      }
    }
  }

  public void resize() {}
}
