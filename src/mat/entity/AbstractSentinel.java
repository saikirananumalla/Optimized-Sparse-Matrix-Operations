package mat.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSentinel extends Sentinel{

  protected AbstractSentinel(){
    resetThis();
    resetIndices();
  }

  protected void add(int index, Node n){
    Node pos = getNodeAtIndex(index);
    addAtNode(n, pos);
  }

  @Override
  protected float getValue(int index) {
    Node n = getNodeAtIndex(index);

    if( n == this || getIndex((DataNode) n) != index) return 0;
    return ((DataNode)n).getData();
  }

  @Override
  protected void remove(int index) {
    Node n = getNodeAtIndex(index);
    indices.remove((Integer) index);
    ((DataNode)n).remove();
  }

  @Override
  protected void setValue(int index, float data) {
    Node n = getNodeAtIndex(index);
    ((DataNode)n).setData(data);
  }

  protected void setIdentity(DataNode d){
      resetThis();
      resetIndices();
      addAtNode(d, getNext(this));
  }

  protected List<Integer> getIndices(){
    Collections.sort(indices);
    return indices;
  }

  protected abstract int getIndex(DataNode c);

  protected abstract Node getPrev(Node n);

  protected abstract void setPrev(Node n, Node prev);

  protected abstract Node getNext(Node n);

  protected abstract void setNext(Node n, Node next);

  private void addAtNode(Node n, Node pos) {
    setNext(n, pos);
    setPrev(n, getPrev(pos));
    setNext(getPrev(pos), n);
    setPrev(pos, n);

    indices.add(getIndex((DataNode)n));
  }

  private Node getNodeAtIndex(int index) {
    Node c = getNext(this);

    while(c!=this && index < getIndex((DataNode)(c))){
      c = getNext(c);
    }
    return c;
  }

  private void resetThis() {
    setPrev(this, this);
    setNext(this, this);
  }

  private void resetIndices() {
    indices = new ArrayList<>();
  }

}
