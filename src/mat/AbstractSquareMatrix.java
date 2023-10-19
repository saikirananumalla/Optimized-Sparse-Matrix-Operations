package mat;

import java.util.List;

import mat.entity.Pair;

/**
 * A class to abstract square matrix validations and implementations including a double dispatch
 * based on the subtype of matrices to be operated upon.
 */
public abstract class AbstractSquareMatrix implements SquareMatrix {

  protected int size;

  @Override
  public int size() {
    return size;
  }

  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    return other.premul(this);
  }

  /**
   * Add array matrix factory method, this method adds array matrix to the matrix type that
   * implements this.
   *
   * @param arr ArrayMatrix
   * @return new SquareMatrix as result
   */
  protected abstract SquareMatrix addArrayMatrix(ArrayMatrix arr);

  /**
   * Add array matrix factory method, this method adds array matrix to the matrix type that
   * implements this.
   *
   * @param spm SparseMatrix
   * @return new SquareMatrix as result
   */
  protected abstract SquareMatrix addSparseMatrix(SparseMatrix spm);

  /**
   * Post multiply array matrix factory method, this method adds array matrix to the matrix type
   * that implements this.
   *
   * @param arr ArrayMatrix
   * @return new SquareMatrix as result
   */
  protected abstract SquareMatrix postMulArrayMatrix(ArrayMatrix arr);

  /**
   * Post multiply array matrix factory method, this method adds array matrix to the matrix type
   * that implements this.
   *
   * @param spm SparseMatrix
   * @return new SquareMatrix as result
   */
  protected abstract SquareMatrix postMulSparseMatrix(SparseMatrix spm);

  /**
   * Validate size if non-negative.
   *
   * @param size size of matrix
   */
  protected static void validateSize(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("The size of an array matrix cannot be non-positive");
    }
  }

  /**
   * Check if two matrices have same size.
   *
   * @param other other matrix to be checked
   */
  protected void validateEqualSize(SquareMatrix other) {
    if (other == null || this.size() != other.size()) {
      throw new IllegalArgumentException("The dimensions of the two matrices do not match");
    }
  }

  /**
   * Validate if indexes lie in 0 to size range.
   *
   * @param i row number
   * @param j column number
   */
  protected void validateIndices(int i, int j) {
    if ((i < 0) || (i >= size) || (j < 0) || (j >= size)) {
      throw new IllegalArgumentException("Index cannot be outside 0 and size");
    }
  }

  protected static void addRowsMixed(SquareMatrix a, List<Pair> cols,
                                     SquareMatrix result, int row, int size) {
    for (int i = 0; i < size; i++) {
      result.set(row, i, a.get(row, i));
    }

    for (Pair col : cols) {
      result.set(row, col.index, result.get(row, col.index) + col.value);
    }
  }

}
