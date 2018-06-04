package com.chudakov.algorithms.data_structures;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
  private Map<Integer, Node> nodeMap;
  private Node first = null;
  private Node last = null;
  private int capacity;
  private int size = 0;

  public LRUCache(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("Capacity can’t be smaller than 1");
    }
    this.capacity = capacity;
    this.nodeMap = new HashMap<>();
  }

  public int refer(int a) {
    Node nodeFromMap = nodeMap.get(a);
    if (nodeFromMap == null) {
      Node node = new Node(a);
      nodeMap.put(a, node);
      if (size == capacity) {
        nodeMap.remove(last.data);
        unlink(last);
        linkFirst(node);
      } else {
        linkFirst(node);
        ++size;
      }
      return -1;
    } else {
      unlink(nodeFromMap);
      linkFirst(nodeFromMap);
      return a;
    }
  }

  private void linkFirst(Node node) {
    node.next = first;
    if (first != null) {
      first.prev = node;
    }
    if (last == null) {
      last = node;
    }
    first = node;
  }

  private void unlink(Node node) {
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    if (node.prev != null) {
      node.prev.next = node.next;
    }
    if (node == first) {
      first = node.next;
    }
    if (node == last) {
      last = node.prev;
    }
  }

  private class Node {
    int data;
    Node next;
    Node prev;

    public Node(int data) {
      this.data = data;
    }
  }
}
