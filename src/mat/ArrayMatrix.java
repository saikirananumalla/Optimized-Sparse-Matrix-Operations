package mat;

import java.util.Arrays;
import java.util.List;

import mat.entity.Pair;

/**
 * This class implements a 2D matrix of numbers using a 2D array. This implementation is efficient
 * if most of the entries of the matrix are not zero. But this wastes a lot of space and computing
 * time if most of its entries are zero.
 */
public class ArrayMatrix extends AbstractSquareMatrix {

  private final float[][] mat;

  /**
   * Constructs a new matrix of the given dimensions. All entries of this matrix are by default 0.
   *
   * @param size the number of rows and columns in this matrix
   * @throws IllegalArgumentException if the size is a non-positive number
   */
  public ArrayMatrix(int size) throws IllegalArgumentException {
    validateSize(size);
    this.size = size;

    mat = new float[size][size];
    for (float[] floats : mat) {
      Arrays.fill(floats, 0.0f);
    }
  }

  @Override
  public void setIdentity() {
    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        if (i == j) {
          mat[i][j] = 1;
        } else {
          mat[i][j] = 0;
        }
      }
    }
  }

  @Override
  public void set(int i, int j, float value) throws IllegalArgumentException {
    validateIndices(i, j);
    mat[i][j] = value;
  }

  @Override
  public float get(int i, int j) throws IllegalArgumentException {
    validateIndices(i, j);
    return mat[i][j];
  }

  @Override
  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    return ((AbstractSquareMatrix) other).addArrayMatrix(this);
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    return ((AbstractSquareMatrix) other).postMulArrayMatrix(this);
  }

  @Override
  protected SquareMatrix addArrayMatrix(ArrayMatrix arr) {
    SquareMatrix result = new ArrayMatrix(size);

    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        result.set(i, j, this.get(i, j) + arr.get(i, j));
      }
    }

    return result;
  }

  @Override
  protected SquareMatrix addSparseMatrix(SparseMatrix spm) {
    SquareMatrix result = new ArrayMatrix(size);

    for (int i = 0; i < size; ++i) {
      List<Pair> cols = spm.getRow(i).getIndices();
      addRowsMixed(this, cols, result, i, size);
    }

    return result;
  }

  @Override
  protected SquareMatrix postMulArrayMatrix(ArrayMatrix arr) {
    SquareMatrix result = new ArrayMatrix(size);

    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        float sum = 0;
        for (int k = 0; k < size; k += 1) {
          sum += get(i, k) * arr.get(k, j);
        }
        result.set(i, j, sum);
      }
    }

    return result;
  }

  @Override
  protected SquareMatrix postMulSparseMatrix(SparseMatrix spm) {
    SquareMatrix result = new ArrayMatrix(size);

    for (int i = 0; i < size; ++i) {
      List<Pair> col = spm.getCol(i).getIndices();

      for (int j = 0; j < size; ++j) {

        float sum = 0;

        for (Pair pair : col) {
          sum += (pair.value * get(j, pair.index));
        }

        if (sum != 0) {
          result.set(j, i, sum);
        }
      }
    }

    return result;
  }
}
