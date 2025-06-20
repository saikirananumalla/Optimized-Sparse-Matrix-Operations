package mat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent the starting node of a linked list as a sentinel, this ideally takes care
 * of the add, get, set and delete, and other operations of a linked list.
 */
public abstract class Sentinel extends Node {

  /**
   * Gets the data at a given index in the list starting with this sentinel.
   *
   * @param index index of the node
   * @return float value of data in the node
   */
  public float get(int index) {
    validateIndex(index);

    Node n = getNodeAtIndex(index);

    if (n == this || getIndex((DataNode) n) != index) {
      return 0;
    }
    return ((DataNode) n).getData();
  }

  /**
   * Create a new instance of sentinel with this next and prev pointing to this and indices is an
   * empty list.
   */
  protected Sentinel() {
    resetThis();
  }

  /**
   * Adds the dataNode at a given index, or updates data value if index is same in the list
   * starting with this sentinel.
   *
   * @param index index to be inserted at
   * @param n     DataNode to be inserted
   */
  public void add(int index, DataNode n) {
    Node pos = getNodeAtIndex(index);
    if (pos != this && getIndex((DataNode) pos) == getIndex(n)) {
      ((DataNode) pos).setData(n.getData());
      return;
    }
    addAtNode(n, pos);
  }

  /**
   * Delete the data node at the index if the index exists in the list headed by this sentinel.
   *
   * @param index index of node
   */
  public void remove(int index) {
    Node n = getNodeAtIndex(index);

    if (n == this || index != (getIndex((DataNode) n))) {
      return;
    }

    ((DataNode) n).remove();
  }

  /**
   * Reset sentinel as in empty list and append a single data node.
   *
   * @param d data node to be appended
   */
  public void setIdentity(DataNode d) {
    resetThis();
    addAtNode(d, this);
  }

  /**
   * Gets the list of non-zero row indexed for a column sentinel and vice-versa.
   *
   * @return list of indexes
   */
  public List<Pair> getIndices() {
    List<Pair> res = new ArrayList<>();
    Node c = getNext(this);
    while (c != this) {
      res.add(new Pair(getIndex((DataNode) c), ((DataNode) c).getData()));
      c = getNext(c);
    }
    return res;
  }


  /**
   * Gets the row index for a column sentinel and vice-versa.
   *
   * @param c data node
   * @return index of node
   */
  protected abstract int getIndex(DataNode c);

  /**
   * Gets the previous node of this node, left if row sentinel and up if column sentinel.
   *
   * @param n data node
   * @return previous node
   */
  protected abstract Node getPrev(Node n);

  /**
   * Sets the previous node of this node, left if row sentinel and up if column sentinel.
   *
   * @param n data node
   */
  protected abstract void setPrev(Node n, Node prev);

  /**
   * Gets the next node of this node, right if row sentinel and down if column sentinel.
   *
   * @param n data node
   * @return next node
   */
  protected abstract Node getNext(Node n);

  /**
   * Sets the next node of this node, right if row sentinel and down if column sentinel.
   *
   * @param n data node
   */
  protected abstract void setNext(Node n, Node next);

  private void addAtNode(Node n, Node pos) {
    setNext(n, pos);
    setPrev(n, getPrev(pos));
    setNext(getPrev(pos), n);
    setPrev(pos, n);
  }

  private Node getNodeAtIndex(int index) {
    Node c = getNext(this);

    while (c != this && index > getIndex((DataNode) (c))) {
      c = getNext(c);
    }
    return c;
  }

  private void resetThis() {
    setPrev(this, this);
    setNext(this, this);
  }

  private void validateIndex(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Invalid index");
    }
  }

}
