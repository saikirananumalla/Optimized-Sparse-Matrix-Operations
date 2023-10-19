package mat;

import java.util.ArrayList;
import java.util.List;

import mat.entity.ColSentinel;
import mat.entity.DataNode;
import mat.entity.Pair;
import mat.entity.RowSentinel;

/**
 * This class implements a 2D matrix of numbers using Linked Lists. This implementation is efficient
 * if most of the entries of the matrix are zero. But this wastes a lot of computing time
 * if most of its entries are not zero, as set and get i, j is costly.At any given instance, this
 * matrix only contains nonzero nodes in a linked fashion.
 */
public class SparseMatrix extends AbstractSquareMatrix {

  private final List<RowSentinel> rows;

  private final List<ColSentinel> cols;

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
      rows.add(new RowSentinel());
      cols.add(new ColSentinel());
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
  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    return ((AbstractSquareMatrix) other).addSparseMatrix(this);
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    return ((AbstractSquareMatrix) other).postMulSparseMatrix(this);
  }

  /**
   * Gets the list of row sentinels representing each row in the matrix.
   *
   * @return row sentinel list
   */
  public RowSentinel getRow(int index) {
    return rows.get(index);
  }

  /**
   * Gets the column sentinels representing each column in the matrix.
   *
   * @return column sentinel list
   */
  public ColSentinel getCol(int index) {
    return cols.get(index);
  }

  @Override
  protected SquareMatrix addArrayMatrix(ArrayMatrix arr) {
    SquareMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; ++i) {
      List<Pair> cols = rows.get(i).getIndices();
      addRowsMixed(arr, cols, result, i, size);
    }

    return result;
  }

  @Override
  protected SquareMatrix addSparseMatrix(SparseMatrix spm) {
    SquareMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i++) {
      List<Pair> cols = rows.get(i).getIndices();
      List<Pair> otherCols = spm.getRow(i).getIndices();
      addRows(otherCols, cols, result, i);
    }

    return result;
  }

  @Override
  protected SquareMatrix postMulArrayMatrix(ArrayMatrix arr) {
    SquareMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; ++i) {
      List<Pair> row = rows.get(i).getIndices();

      for (int j = 0; j < size; ++j) {
        float sum = 0;

        for (Pair pair : row) {
          sum += (pair.value * arr.get(pair.index, j));
        }

        if (sum != 0) {
          result.set(i, j, sum);
        }
      }
    }

    return result;
  }

  @Override
  protected SquareMatrix postMulSparseMatrix(SparseMatrix spm) {
    SquareMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i += 1) {
      RowSentinel row = rows.get(i);

      for (int j = 0; j < size; j += 1) {
        ColSentinel col = spm.getCol(j);
        result.set(i, j, row.multiplyColSentinel(col));
      }
    }

    return result;
  }

  private void addRows(List<Pair> cols1, List<Pair> cols2, SquareMatrix result, int row) {
    int a = 0;
    int c = cols1.size();

    int b = 0;
    int d = cols2.size();

    while (a < c && b < d) {
      Pair colA = cols1.get(a);
      Pair colB = cols2.get(b);

      if (colA.index < colB.index) {
        result.set(row, colA.index, colA.value);
        a++;
      } else if (colA.index > colB.index) {
        result.set(row, colB.index, colB.value);
        b++;
      } else {
        result.set(row, colA.index, colA.value + colB.value);
        a++;
        b++;
      }
    }

    while (a < c) {
      Pair colA = cols1.get(a);
      result.set(row, colA.index, colA.value);
      a++;
    }

    while (b < d) {
      Pair colB = cols2.get(b);
      result.set(row, colB.index, colB.value);
      b++;
    }
  }

}
