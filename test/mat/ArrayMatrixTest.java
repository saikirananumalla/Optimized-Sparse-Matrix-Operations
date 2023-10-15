package mat;

/**
 * A JUnit class to test the ArrayMatrix class and other matrix operations.
 */
public class ArrayMatrixTest extends AbstractSquareMatrixTest {

  @Override
  protected SquareMatrix getNewMatrix(int size) {
    return new ArrayMatrix(size);
  }

  @Override
  protected SquareMatrix getOtherMatrix(int size) {
    return new SparseMatrix(size);
  }
}