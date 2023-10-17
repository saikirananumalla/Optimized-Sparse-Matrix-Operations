package mat.entity;

import java.util.List;

/**
 * An abstract class to implement the link list class using a head sentinel and data nodes.
 */
public abstract class AbstarctLinkList implements LinkList {

  private final Sentinel head;

  protected AbstarctLinkList(Sentinel head) {
    this.head = head;
  }

  public void add(int index, DataNode n) {
    head.add(index, n);
  }

  @Override
  public float get(int index) {
    return head.get(index);
  }

  @Override
  public void remove(int index) {
    head.remove(index);
  }

  public void setIdentity(DataNode d) {
    head.setIdentity(d);
  }

  public List<Integer> getIndices() {
    return head.getIndices();
  }
}
