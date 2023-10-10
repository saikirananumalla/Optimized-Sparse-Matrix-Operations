package mat.entity;

import java.util.List;

public abstract class Sentinel extends Node {

  protected List<Integer> indices;

  public float get(int index){
    validateIndex(index);
    return getValue(index);
  }

  public int size(){
    return indices.size();
  }

  protected abstract List<Integer> getIndices();

  protected abstract void add(int index, Node n);

  protected abstract float getValue(int index);

  protected abstract void remove(int index);

  protected abstract void setValue(int index, float data);

  protected abstract void setIdentity(DataNode d);

  private void validateIndex(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Invalid index");
    }
  }
}
