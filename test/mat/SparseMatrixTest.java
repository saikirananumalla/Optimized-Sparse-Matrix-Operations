package mat;

/**
 * A JUnit class to test the SparseMatrix class and other matrix operations.
 */
public class SparseMatrixTest extends AbstractSquareMatrixTest {

  @Override
  protected SquareMatrix getNewMatrix(int size) {
    return new SparseMatrix(size);
  }

  @Override
  protected SquareMatrix getOtherMatrix(int size) {
    return new ArrayMatrix(size);
  }
}

