package mat.entity;

public class ColSentinel extends AbstractSentinel{

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
