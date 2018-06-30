package com.chudakov.data_structures.map;

public class MyHashMap<K, V> {
  private static final int DEFAULT_SIZE = 16;
  Node<K, V>[] table;
  private int size;

  @SuppressWarnings("unchecked")
  public MyHashMap(int size) {
    if (size < 1) {
      throw new IllegalArgumentException();
    }
    this.size = getAppropriateSize(size);
    table = (Node<K, V>[]) new Node[size];
  }

  private int getAppropriateSize(int size) {
    size -= 1;
    size |= size >> 1;
    size |= size >> 2;
    size |= size >> 4;
    size |= size >> 8;
    size |= size >> 16;
    return size + 1;
  }

  public V put(K key, V value) {
    int hash = key.hashCode();
    Node<K, V> node = getNode(hash, key);
    if (node == null) {
      int pos = hash & (size - 1);
      Node<K, V> newNode = new Node<>(hash, key, value);
      insertFirst(newNode, pos);
      return null;
    } else {
      V old = node.value;
      node.value = value;
      return old;
    }
  }

  private void insertFirst(Node<K, V> node, int pos) {
    if (table[pos] == null) {
      table[pos] = node;
    } else {
      Node<K, V> first = table[pos];
      first.prev = node;
      node.next = first;
      table[pos] = first;
    }
  }

  public V get(K key) {
    Node<K, V> node = getNode(key.hashCode(), key);
    return node == null ? null : node.value;
  }

  private Node<K, V> getNode(int hash, K key) {
    int pos = hash & (size - 1);
    Node<K, V> current = table[pos];
    while (current != null) {
      if (current.hash == hash) {
        if (current.key.equals(key)) {
          return current;
        }
      }
      current = current.next;
    }
    return null;
  }

  private class Node<K, V> {
    int hash;
    K key;
    V value;
    Node<K, V> next;
    Node<K, V> prev;

    public Node(int hash, K key, V value) {
      this.hash = hash;
      this.key = key;
      this.value = value;
    }
  }
}
