package mat.entity;

/**
 * A class to extend the sentinel implementation to a row sentinel where a node's next will be
 * right and prev is left.
 */
public class RowSentinel extends Sentinel {

  /**
   * Multiply the values of a list if they have same result and add to result headed by this list.
   *
   * @param sentinel head sentinel
   * @return float values sum of squares of data with same index;
   */
  public float multiplyColSentinel(ColSentinel sentinel) {
    Node a = this.getNext(this);
    Node b = sentinel.getNext(sentinel);

    float res = 0;

    while (a instanceof DataNode && b instanceof DataNode) {
      if (this.getIndex((DataNode) a) < sentinel.getIndex((DataNode) b)) {
        a = getNext(a);
      } else if (this.getIndex((DataNode) a) > sentinel.getIndex((DataNode) b)) {
        b = sentinel.getNext(b);
      } else {
        res += (((DataNode) a).getData() * ((DataNode) b).getData());
        a = getNext(a);
        b = sentinel.getNext(b);
      }
    }

    return res;
  }

  @Override
  protected int getIndex(DataNode c) {
    return c.getCol();
  }

  @Override
  protected Node getPrev(Node n) {
    return n.left;
  }

  @Override
  protected void setPrev(Node n, Node prev) {
    n.left = prev;
  }

  @Override
  protected Node getNext(Node n) {
    return n.right;
  }

  @Override
  protected void setNext(Node n, Node next) {
    n.right = next;
  }
}
