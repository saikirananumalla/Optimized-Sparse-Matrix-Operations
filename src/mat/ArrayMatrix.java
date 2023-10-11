package mat;

import java.util.Arrays;

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

  private ArrayMatrix(float[][] mat) throws IllegalArgumentException {
    this.size = mat.length;
    this.mat = mat;
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

    if (other.getClass() == SparseMatrix.class){
      return other.add(this);
    }

    float[][] result = new float[this.size()][this.size()];
    for (int i = 0; i < this.size(); i += 1) {
      for (int j = 0; j < size(); j += 1) {
        result[i][j] = this.mat[i][j] + other.get(i, j);
      }
    }
    return new ArrayMatrix(result);
  }

  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);

    if (other.getClass() == SparseMatrix.class){
      return other.postmul(this);
    }

    float[][] result = new float[this.size()][other.size()];
    for (int i = 0; i < this.size(); i += 1) {
      for (int j = 0; j < other.size(); j += 1) {
        result[i][j] = 0;
        for (int k = 0; k < this.size(); k += 1) {
          result[i][j] += this.mat[i][k] * other.get(k, j);
        }
      }
    }
    return new ArrayMatrix(result);
  }
}
