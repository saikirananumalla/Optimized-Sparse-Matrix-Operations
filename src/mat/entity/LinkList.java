package mat.entity;

import java.util.List;

/**
 * A class to implement the linked list data structure useful for a sparse matrix implementation.
 */
public interface LinkList {

  /**
   * Add a given dataNode at a given index.
   *
   * @param index the index in the list
   * @param n     dataNode to be added
   */
  void add(int index, DataNode n);

  /**
   * Gets the value at a given index in the list.
   *
   * @param index index of the node
   * @return value at that index
   */
  float get(int index);

  /**
   * Delete the node at a given index.
   *
   * @param index index in the list to be deleted
   */
  void remove(int index);

  /**
   * Set a list to identity, i.e., just a single node with value 1.
   *
   * @param d identity dataNode with value 1.
   */
  void setIdentity(DataNode d);

  /**
   * Gets the list of non-zero row indexes for col linked list and vice-versa.
   *
   * @return list of indices.
   */
  List<Integer> getIndices();
}

