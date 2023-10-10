package mat.entity;

import java.util.List;

public interface LinkList {

  void add(int index, Node n);

  float get(int index);

  void set(int index, float value);

  void remove(int index);

  void setIdentity(DataNode d);

  List<Integer> getIndices();
}

