package mat;

import java.util.ArrayList;
import java.util.List;

import mat.entity.ColLinkList;
import mat.entity.DataNode;
import mat.entity.LinkList;
import mat.entity.RowLinkList;

/**
 * This class implements a 2D matrix of numbers using Linked Lists. This implementation is efficient
 * if most of the entries of the matrix are zero. But this wastes a lot of computing time
 * if most of its entries are not zero, as set and get i, j is costly.
 */
public class SparseMatrix extends AbstractSquareMatrix {

  public final List<LinkList> rows;

  public final List<LinkList> cols;

  /**
   * Constructs new matrix of the given dimensions. All row and col link lists are empty by default.
   *
   * @param size the number of rows and columns in this matrix
   * @throws IllegalArgumentException if the size is a non-positive number
   */
  public SparseMatrix(int size) {
    validateSize(size);

    rows = new ArrayList<>();
    cols = new ArrayList<>();
    this.size = size;

    for (int i = 0; i < size; ++i) {
      rows.add(new RowLinkList());
      cols.add(new ColLinkList());
    }

  }

  @Override
  public void setIdentity() {
    for (int i = 0; i < size; ++i) {
      DataNode d = new DataNode(i, i, 1);
      rows.get(i).setIdentity(d);
      cols.get(i).setIdentity(d);
    }
  }

  @Override
  public void set(int i, int j, float value) throws IllegalArgumentException {
    validateIndices(i, j);

    if (value == 0) {
      rows.get(i).remove(j);
      return;
    }

    DataNode n = new DataNode(i, j, value);
    rows.get(i).add(j, n);
    cols.get(j).add(i, n);
  }

  @Override
  public float get(int i, int j) throws IllegalArgumentException {
    validateIndices(i, j);
    return rows.get(i).get(j);
  }

  @Override
  protected SquareMatrix getNewMatrix(int size) {
    return new SparseMatrix(size);
  }

}
