package com.chudakov.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFUCache {
  Node head;
  Map<Integer, Node> keyMap;
  Map<Integer, Node> nodeMap;
  private int capacity;
  private int size = 0;

  public LFUCache(int capacity) {
    this.capacity = capacity;
    keyMap = new HashMap<>();
    nodeMap = new HashMap<>();
  }

  public void put(int key, int value) {
    if (nodeMap.containsKey(key)) {
      increaseRefNumber(key);
    } else {
      if (size < capacity) {
        Node node = new Node(key, value);
        if (nodeMap.containsKey(1)) {
          Node prev = nodeMap.get(1);
          linkAfter(prev, node);
        } else {
          linkFirst(node);
        }
        nodeMap.put(1, node);
        keyMap.put(key, node);
        ++size;
      } else {
        unlink(head);
        Node node = new Node(key, value);
        Node prev = nodeMap.get(1);
        if (prev == null) {
          linkFirst(node);
        } else {
          linkAfter(prev, node);
        }
        nodeMap.put(1, node);
        keyMap.put(key, node);
      }
    }
  }

  public int get(int key) {
    if (keyMap.containsKey(key)) {
      increaseRefNumber(key);
      return keyMap.get(key).value;
    } else {
      return -1;
    }
  }

  private void increaseRefNumber(int key) {
    Node node = keyMap.get(key);
    int refNum = node.refNumber;
    node.refNumber = node.refNumber + 1;
    if (nodeMap.containsKey(refNum + 1)) {
      Node prev = nodeMap.get(refNum + 1);
      if (node.prev != null && node.prev.refNumber == refNum) {
        nodeMap.put(refNum, node.prev);
      } else {
        nodeMap.remove(refNum);
      }
      linkAfter(prev, node);
      nodeMap.put(refNum + 1, node);
    } else {
      Node prev = nodeMap.get(refNum);
      if (prev != node) {
        linkAfter(prev, node);
      } else {
        if (node.prev != null && node.prev.refNumber == refNum) {
          nodeMap.put(refNum, node.prev);
        } else {
          nodeMap.remove(refNum);
        }
      }
      nodeMap.put(refNum + 1, node);
    }
  }

  private void unlink(Node node) {
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    if (node.prev != null) {
      node.prev.next = node.next;
    }
    if (head == node) {
      head = node.next;
    }
  }

  private void linkFirst(Node node) {
    node.next = head;
    if (head != null) {
      head.prev = node;
    }
    head = node;
  }

  private void linkAfter(Node prev, Node node) {
    String str = "ss";

    node.next = prev.next;
    node.prev = prev;
    if (prev.next != null) {
      prev.next.prev = node;
    }
    prev.next = node;
  }

  public class Node {
    Node next;
    Node prev;
    int refNumber;
    int key;
    int value;

    public Node(int key, int value) {
      this.key = key;
      this.value = value;
      this.refNumber = 1;
    }
  }
}
