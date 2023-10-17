package mat;

/**
 * A class to abstract square matrix validations and implementations.
 */
public abstract class AbstractSquareMatrix implements SquareMatrix {

  protected int size;

  @Override
  public int size() {
    return size;
  }

  @Override
  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    return performOperation(other, '+');
  }

  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    return performOperation(other, '*');
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    return other.postmul(this);
  }

  /**
   * Gets a new matrix based on the subclass.
   *
   * @param size size of the new matrix
   * @return new Square matrix
   */
  protected abstract SquareMatrix getNewMatrix(int size);

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
    if (this.size() != other.size()) {
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

  private SquareMatrix performOperation(SquareMatrix other, char c) {
    validateEqualSize(other);
    SquareMatrix result = getNewMatrix(size);
    operate(other, result, c);
    return result;
  }

  private void operate(SquareMatrix other, SquareMatrix result, char c) {
    if (c == '+') {
      MatrixOperation.add(this, other, result);
    } else {
      MatrixOperation.multiply(this, other, result);
    }
  }
}
