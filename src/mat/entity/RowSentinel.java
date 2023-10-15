package mat.entity;

/**
 * A class to extend the sentinel implementation to a row sentinel where a node's next will be
 * right and prev is left.
 */
public class RowSentinel extends Sentinel {

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
