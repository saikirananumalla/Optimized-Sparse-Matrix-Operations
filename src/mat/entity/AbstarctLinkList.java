package mat.entity;

import java.util.List;

public abstract class AbstarctLinkList implements LinkList {

  private final Sentinel head;

  protected AbstarctLinkList(Sentinel head) {
    this.head = head;
  }

  public void add(int index, Node n){
    head.add(index, n);
  }

  @Override
  public float get(int index) {
    return head.get(index);
  }

  public void set(int index, float value){
    head.setValue(index, value);
  }

  @Override
  public void remove(int index) {
    head.remove(index);
  }

  public void setIdentity(DataNode d){
    head.setIdentity(d);
  }

  public List<Integer> getIndices(){
    return head.getIndices();
  }
}
