package mat.entity;

/**
 * A class to extend the sentinel implementation to a column sentinel where a node's next will be
 * down and prev is up.
 */
public class ColSentinel extends Sentinel {

  protected int getIndex(DataNode c) {
    return c.getRow();
  }

  @Override
  protected Node getPrev(Node n) {
    return n.up;
  }

  @Override
  protected void setPrev(Node n, Node prev) {
    n.up = prev;
  }

  @Override
  protected Node getNext(Node n) {
    return n.down;
  }

  @Override
  protected void setNext(Node n, Node next) {
    n.down = next;
  }
}
