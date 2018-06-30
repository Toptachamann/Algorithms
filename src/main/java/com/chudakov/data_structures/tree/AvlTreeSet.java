package com.chudakov.data_structures.tree;

import java.util.ArrayList;
import java.util.List;

public class AvlTreeSet<K extends Comparable<K>> {
  private final Node dummyNode = new Node();
  int size = 0;
  private Node root;

  public List<K> getSorted() {
    List<K> sorted = new ArrayList<>(size);
    if (root != null) {
      inOrderTraversal(sorted, root);
    }
    return sorted;
  }

  public List<K> getPreOrder() {
    List<K> list = new ArrayList<>();
    if (root != null) {
      preOrder(list, root);
    }
    return list;
  }

  public int size() {
    return size;
  }

  public void add(K key) {
    Node node = searchNode(key);
    if (node == null) {
      node = new Node(key);
      insertNode(node);
      size++;
    }
  }

  public void remove(K key) {
    Node node = searchNode(key);
    if (node != null) {
      deleteNode(node);
      size--;
    }
  }

  public boolean contains(K key) {
    return searchNode(key) != null;
  }

  public K next(K key) {
    if (size == 0) {
      return null;
    }
    Node node = searchNode(key);
    if (node == null) {
      node = getPosToInsert(key);
      if (node.key.compareTo(key) > 0) {
        return node.key;
      } else {
        return next(node);
      }
    } else {
      return next(node);
    }
  }

  public K prev(K key) {
    if (size == 0) {
      return null;
    }
    Node node = searchNode(key);
    if (node == null) {
      node = getPosToInsert(key);
      if (node.key.compareTo(key) < 0) {
        return node.key;
      } else {
        return prev(node);
      }
    } else {
      return prev(node);
    }
  }

  public K orderStatistics(int k) {
    if (k < 1 || k > size) {
      return null;
    } else {
      Node current = root;
      int order;
      while (true) {
        order = current.left.weight + 1;
        if (order == k) {
          return current.key;
        } else if (order > k) {
          current = current.left;
        } else {
          k -= order;
          current = current.right;
        }
      }
    }
  }

  public K min() {
    if (size == 0) {
      return null;
    } else {
      return min(root).key;
    }
  }

  public boolean checkIsAvl() {
    return root == null || checkIsAvl(root) != -1;
  }

  private void inOrderTraversal(List<K> list, Node current) {
    if (current != dummyNode) {
      inOrderTraversal(list, current.left);
      list.add(current.key);
      inOrderTraversal(list, current.right);
    }
  }

  private void preOrder(List<K> list, Node current) {
    if (current != dummyNode) {
      list.add(current.key);
      preOrder(list, current.left);
      list.add(current.key);
      preOrder(list, current.right);
      list.add(current.key);
    }
  }

  private int checkIsAvl(Node current) {
    if (current != dummyNode) {
      if (current.isUnbalanced()) {
        return -1;
      } else {
        int weightLeft = checkIsAvl(current.left);
        int weightRight = checkIsAvl(current.right);
        if (weightLeft == -1 || weightRight == -1) {
          return -1;
        } else if (current.weight != weightLeft + weightRight + 1) {
          return -1;
        } else {
          return weightLeft + weightRight + 1;
        }
      }
    } else {
      return 0;
    }
  }

  private void deleteNode(Node node) {
    if (node.left == dummyNode || node.right == dummyNode) {
      deleteNodeWithoutChild(node);
      if (node.parent != null) {
        balance(node.parent);
      }
    } else {
      Node min = min(node.right);
      Node parentMin = min == node.right ? min : min.parent;
      deleteNodeWithoutChild(min);
      substituteNode(node, min);
      if (parentMin != null) {
        balance(parentMin);
      }
    }
  }

  private void deleteNodeWithoutChild(Node node) {
    Node notDummy = node.left == dummyNode ? node.right : node.left;
    if (notDummy == dummyNode) {
      if (node.parent == null) {
        root = null;
      } else {
        node.parent.substituteChild(node, dummyNode);
      }
    } else {
      if (node.parent == null) {
        notDummy.parent = null;
        root = notDummy;
      } else {
        node.parent.substituteChild(node, notDummy);
      }
    }
  }

  private void substituteNode(Node oldNode, Node newNode) {
    if (oldNode.parent == null) {
      newNode.parent = null;
      root = newNode;
    } else {
      oldNode.parent.substituteChild(oldNode, newNode);
    }
    oldNode.left.parent = oldNode.right.parent = newNode;
    newNode.left = oldNode.left;
    newNode.right = oldNode.right;
  }

  private Node min(Node start) {
    Node current = start;
    while (current.left != dummyNode) {
      current = current.left;
    }
    return current;
  }

  private Node max(Node start) {
    Node current = start;
    while (current.right != dummyNode) {
      current = current.right;
    }
    return current;
  }

  private K next(Node node) {
    if (node.right != dummyNode) {
      return min(node.right).key;
    } else {
      while (node != root) {
        if (node.isLeftChild()) {
          return node.parent.key;
        }
        node = node.parent;
      }
      return null;
    }
  }

  private K prev(Node node) {
    if (node.left != dummyNode) {
      return max(node.left).key;
    } else {
      while (node != root) {
        if (node.isRightChild()) {
          return node.parent.key;
        }
        node = node.parent;
      }
      return null;
    }
  }

  private Node searchNode(K key) {
    if (root == null) {
      return null;
    } else {
      Node front = root;
      while (front != dummyNode && !key.equals(front.key)) {
        front = key.compareTo(front.key) > 0 ? front.right : front.left;
      }
      if (front == dummyNode) {
        return null;
      } else {
        return front;
      }
    }
  }

  private void insertNode(Node node) {
    if (root == null) {
      root = node;
    } else {
      Node parent = getPosToInsert(node.key);
      if (node.key.compareTo(parent.key) > 0) {
        parent.linkRight(node);
      } else {
        parent.linkLeft(node);
      }
      balance(parent);
    }
  }

  private Node getPosToInsert(K key) {
    Node back = root;
    Node front = root;
    while (front != dummyNode) {
      back = front;
      front = key.compareTo(front.key) > 0 ? front.right : front.left;
    }
    return back;
  }

  private void balance(Node start) {
    Node current = start;
    while (current != null) {
      balanceNode(current);
      current = current.parent;
    }
  }

  private void rotateRight(Node y) {
    Node x = y.left;
    Node parent = y.parent;

    x.right.parent = y;
    y.left = x.right; // plant B from x to y

    y.parent = x;
    x.right = y;

    x.updateHeightAndWeight();
    y.updateHeightAndWeight();

    if (parent == null) {
      root = x;
      x.parent = null;
    } else {
      parent.substituteChild(y, x);
    }
  }

  private void rotateLeft(Node x) {
    Node y = x.right;
    Node parent = x.parent;

    y.left.parent = x;
    x.right = y.left;

    x.parent = y;
    y.left = x;

    x.updateHeightAndWeight();
    y.updateHeightAndWeight();

    if (parent == null) {
      root = y;
      y.parent = null;
    } else {
      parent.substituteChild(x, y);
    }
  }

  private void balanceNode(Node node) {
    node.updateHeightAndWeight();
    if (node.isUnbalanced()) {
      if (node.isRightHeavy()) {
        if (node.right.isRightHeavy()) {
          rotateLeft(node);
        } else {
          rotateRight(node.right);
          rotateLeft(node);
        }
      } else {
        if (node.left.isLeftHeavy()) {
          rotateRight(node);
        } else {
          rotateLeft(node.left);
          rotateRight(node);
        }
      }
    }
  }

  class Node {
    K key;
    Node parent;
    Node left;
    Node right;
    int height;
    int weight;

    Node() {
      height = -1;
      weight = 0;
      parent = left = right = this;
    }

    Node(K key) {
      this.key = key;
      height = 0;
      weight = 1;
      left = right = dummyNode;
    }

    void updateHeightAndWeight() {
      height = Math.max(left.height, right.height) + 1;
      weight = left.weight + right.weight + 1;
    }

    void substituteChild(Node oldChild, Node newChild) {
      if (left == oldChild) {
        left = newChild;
      } else {
        right = newChild;
      }
      newChild.parent = this;
    }

    boolean isRightHeavy() {
      return right.height > left.height;
    }

    boolean isLeftHeavy() {
      return left.height > right.height;
    }

    boolean isUnbalanced() {
      return Math.abs(left.height - right.height) > 1;
    }

    void linkLeft(Node node) {
      left = node;
      node.parent = this;
    }

    void linkRight(Node node) {
      right = node;
      node.parent = this;
    }

    boolean isRightChild() {
      return parent.right == this;
    }

    boolean isLeftChild() {
      return parent.left == this;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("key=").append(key);
      sb.append(", height=").append(height);
      sb.append(", weight=").append(weight);
      sb.append(", left=");
      if (left == dummyNode) {
        sb.append("dummyNode");
      } else {
        sb.append(left.key);
      }
      sb.append(", right=");
      if (right == dummyNode) {
        sb.append("dummyNode");
      } else {
        sb.append(right.key);
      }
      sb.append(", parent=");
      if (parent == null) {
        sb.append("null");
      } else {
        sb.append(parent.key);
      }
      sb.append('}');
      return sb.toString();
    }
  }
}
