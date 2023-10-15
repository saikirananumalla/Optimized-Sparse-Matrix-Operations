import org.junit.Before;
import org.junit.Test;

import mat.SquareMatrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit class to abstract the testing of array and sparse matrices.
 */
public abstract class AbstractSquareMatrixTest {

  public static final char ADD = '+';
  public static final char POST_MUL = '*';
  public static final char PRE_MUL = '/';

  protected SquareMatrix sqm;

  @Before
  public void setUp() {
    sqm = getNewMatrix(4);

    sqm.set(0, 0, 1);
    sqm.set(1, 1, 2);
    sqm.set(2, 0, 5);
    sqm.set(2, 2, 3);
    sqm.set(2, 1, 1.5f);
    sqm.set(3, 1, 2.54f);
    sqm.set(3, 3, 10.125f);
  }

  protected abstract SquareMatrix getNewMatrix(int size);

  protected abstract SquareMatrix getOtherMatrix(int size);

  @Test(expected = IllegalArgumentException.class)
  public void testSetUp_NegativeSize() {
    getNewMatrix(-2);
  }

  @Test
  public void testSetUp_ZeroSize() {
    SquareMatrix sqm1 = getNewMatrix(0);
    SquareMatrix sqm2 = getNewMatrix(0);
    SquareMatrix res = performOperation(sqm2, sqm1, ADD);

    assertEquals(0, res.size());

  }

  @Test
  public void testSetIdentity() {
    sqm.setIdentity();

    assertEquals(1, sqm.get(0, 0), 0.001);
    assertEquals(1, sqm.get(1, 1), 0.001);
    assertEquals(1, sqm.get(2, 2), 0.001);
    assertEquals(0, sqm.get(2, 0), 0.001);
    assertEquals(0, sqm.get(1, 2), 0.001);

    sqm.set(1, 2, 5.2342344f);
    assertEquals(5.2342344, sqm.get(1, 2), 0.001);

    SquareMatrix sqm1 = getNewMatrix(4);
    sqm1.setIdentity();

    assertEquals(1, sqm1.get(0, 0), 0.001);
    assertEquals(1, sqm1.get(1, 1), 0.001);
    assertEquals(1, sqm1.get(2, 2), 0.001);
    assertEquals(1, sqm1.get(3, 3), 0.001);
    assertEquals(0, sqm1.get(3, 1), 0.001);
    assertEquals(0, sqm1.get(1, 3), 0.001);

    sqm1.set(1, 2, 5.2342344f);
    sqm1.set(3, 3, 6.2342344f);
    assertEquals(5.2342344, sqm1.get(1, 2), 0.001);
    assertEquals(6.2342344, sqm1.get(3, 3), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSet_invalidI() {
    sqm.set(-1, 1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSet_invalidJ() {
    sqm.set(2, -1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSet_outsideSize() {
    sqm.set(2, 4, 4);
  }

  @Test
  public void testSet_Valid() {
    sqm.set(2, 3, .45f);
    sqm.set(3, 0, 1.234f);

    assertEquals(.45, sqm.get(2, 3), 0.001);
    assertEquals(1.234, sqm.get(3, 0), 0.001);
  }

  @Test
  public void testSet_Zero() {
    sqm.set(2, 3, 0);

    assertEquals(0, sqm.get(2, 3), 0);
  }

  @Test
  public void testSet_Reset() {
    sqm.set(2, 3, 0);
    assertEquals(0, sqm.get(2, 3), 0);
    sqm.set(2, 3, -2.5f);
    assertEquals(-2.5, sqm.get(2, 3), 0.001);
    sqm.set(2, 3, 7.6f);
    assertEquals(7.6, sqm.get(2, 3), 0.001);
    sqm.set(2, 3, 0);
    assertEquals(0, sqm.get(2, 3), 0);
  }

  @Test
  public void testSet_RepeatedSet() {
    SquareMatrix sqm1 = getNewMatrix(4);

    sqm1.set(0, 0, 1);
    sqm1.set(1, 1, 2);
    sqm1.set(2, 0, 5);
    sqm1.set(2, 2, 3);
    sqm1.set(2, 1, 1.5f);
    sqm1.set(3, 1, 2.54f);
    sqm1.set(3, 3, 10.125f);


    sqm1.set(3, 3, 10.125f);
    sqm1.set(3, 3, 10.125f);
    sqm1.set(3, 3, 10.125f);

    sqm1.set(2, 0, 5);
    sqm1.set(2, 0, 5);
    sqm1.set(2, 0, 5);
    sqm1.set(2, 0, 5);

    sqm1.set(0, 0, 1);
    sqm1.set(0, 0, 1);

    assertEquals(1, sqm1.get(0, 0), 0.001);
    assertEquals(2, sqm1.get(1, 1), 0.001);
    assertEquals(5, sqm1.get(2, 0), 0.001);
    assertEquals(3, sqm1.get(2, 2), 0.001);
    assertEquals(1.5, sqm1.get(2, 1), 0.001);
    assertEquals(2.54, sqm1.get(3, 1), 0.001);
    assertEquals(10.125, sqm1.get(3, 3), 0.001);

    assertEquals(0, sqm1.get(3, 2), 0.001);
    assertEquals(0, sqm1.get(2, 3), 0.001);
    assertEquals(0, sqm1.get(0, 1), 0.001);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGet_invalidI() {
    sqm.get(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGet_invalidJ() {
    sqm.get(2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGet_outsideSize() {
    sqm.get(2, 4);
  }

  @Test
  public void testGet() {
    assertEquals(1, sqm.get(0, 0), 0.001);
    assertEquals(2, sqm.get(1, 1), 0.001);
    assertEquals(3, sqm.get(2, 2), 0.001);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAdd_DiffSize() {
    performOperation(sqm, getNewMatrix(3), ADD);
  }


  @Test
  public void testAdd_SameType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getNewMatrix(4), ADD));
  }

  @Test
  public void testAdd_MixedType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getOtherMatrix(4), ADD));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPostMul_DiffSize() {
    sqm.postmul(getNewMatrix(3));
  }


  @Test
  public void testPostMul_SameType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getNewMatrix(4), POST_MUL));
  }

  @Test
  public void testPostMul_MixedType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getOtherMatrix(4), POST_MUL));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPreMul_DiffSize() {
    sqm.premul(getNewMatrix(3));
  }


  @Test
  public void testPreMul_SameType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getNewMatrix(4), PRE_MUL));
  }

  @Test
  public void testPreMul_MixedType() {
    assertTrue(operateAndTestResult(getNewMatrix(4), getOtherMatrix(4), PRE_MUL));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLargeIdentities_DiffSize() {
    int dim = 1000;
    verifyIdentityOperation(getNewMatrix(1000), getOtherMatrix(2000), dim);
  }

  @Test
  public void testLargeIdentities_SameType() {
    int dim = 2000;
    assertTrue(verifyIdentityOperation(getNewMatrix(dim), getNewMatrix(dim), dim));
  }

  @Test
  public void testLargeIdentities_MixedType() {
    int dim = 2000;
    assertTrue(verifyIdentityOperation(getNewMatrix(dim), getOtherMatrix(dim), dim));
  }

  @Test
  public void testMultiplyWithIdentity_SameType() {
    SquareMatrix s = getNewMatrix(4);
    s.setIdentity();
    assertTrue(isEqual(sqm, sqm.postmul(s)));
    assertTrue(isEqual(sqm, sqm.premul(s)));
  }

  @Test
  public void testMultiplyWithIdentity_DiffType() {
    SquareMatrix s = getOtherMatrix(4);
    s.setIdentity();
    assertTrue(isEqual(sqm, sqm.postmul(s)));
    assertTrue(isEqual(sqm, sqm.premul(s)));
  }

  @Test
  public void testLargeMatrices_SameType() {
    int size = 2000;
    SquareMatrix one = getNewMatrix(size);
    SquareMatrix two = getNewMatrix(size);
    one.setIdentity();
    two.setIdentity();

    one.set(500, 1999, 2.45f);
    two.set(1999, 0, 10);

    SquareMatrix res = performOperation(one, two, ADD);
    assertEquals(2.45, res.get(500, 1999), 0.001);
    assertEquals(10, res.get(1999, 0), 0.001);
    assertEquals(2, res.get(1990, 1990), 0.001);

    res = performOperation(one, two, POST_MUL);
    assertEquals(24.5, res.get(500, 0), 0.001);
  }

  @Test
  public void testLargeMatrices_DiffType() {
    int size = 2000;
    SquareMatrix one = getNewMatrix(size);
    SquareMatrix two = getOtherMatrix(size);
    one.setIdentity();
    two.setIdentity();

    one.set(500, 1999, 2.45f);
    two.set(1999, 0, 10);

    SquareMatrix res = performOperation(one, two, ADD);
    assertEquals(2.45, res.get(500, 1999), 0.001);
    assertEquals(10, res.get(1999, 0), 0.001);
    assertEquals(2, res.get(1990, 1990), 0.001);

    res = one.postmul(two);
    assertEquals(24.5, res.get(500, 0), 0.001);
  }

  private static boolean operateAndTestResult(SquareMatrix one, SquareMatrix two, char c) {
    setMatrixValues(one, two);
    float[][] answer = getAnswer(c);
    SquareMatrix result = performOperation(one, two, c);
    for (int i = 0; i < 4; i += 1) {
      for (int j = 0; j < 4; j += 1) {
        assertEquals(answer[i][j], result.get(i, j), 0.001);
      }
    }
    return true;
  }

  private static SquareMatrix performOperation(SquareMatrix one, SquareMatrix two, char c) {
    if (c == ADD) {
      return one.add(two);
    } else if (c == POST_MUL) {
      return one.postmul(two);
    }
    return one.premul(two);
  }

  private static boolean verifyIdentityOperation(SquareMatrix one, SquareMatrix two, int dim) {
    one.setIdentity();
    two.setIdentity();
    SquareMatrix add = performOperation(one, two, ADD);
    SquareMatrix premul = one.premul(two);
    SquareMatrix postmul = one.postmul(two);

    for (int i = 0; i < dim; i += 1) {
      for (int j = 0; j < dim; j += 1) {
        if (i == j) {
          assertEquals(2, add.get(i, j), 0.001);
          assertEquals(1, premul.get(i, j), 0.001);
          assertEquals(1, postmul.get(i, j), 0.001);
        } else {
          assertEquals(0, add.get(i, j), 0.001);
          assertEquals(0, premul.get(i, j), 0.001);
          assertEquals(0, postmul.get(i, j), 0.001);
        }
      }
    }
    return true;
  }

  private static void setMatrixValues(SquareMatrix one, SquareMatrix two) {
    float[][] onevalues = {
            {1, 2, 3, 4},
            {1, 5, 2, 1},
            {0, 1, -1, 2},
            {0, 0, 1, 0}};

    float[][] twovalues = {
            {2, 1, 3, 1},
            {2, 1, 5, 4},
            {-1, 2, 1, 1},
            {1, 1, 1, 1}
    };

    for (int i = 0; i < 4; i += 1) {
      for (int j = 0; j < 4; j += 1) {
        one.set(i, j, onevalues[i][j]);
        two.set(i, j, twovalues[i][j]);
      }
    }
  }

  private static float[][] getAnswer(char c) {
    switch (c) {
      case ADD:
        return new float[][]{
                {3, 3, 6, 5},
                {3, 6, 7, 5},
                {-1, 3, 0, 3},
                {1, 1, 2, 1}
        };

      case POST_MUL:
        return new float[][]{
                {7, 13, 20, 16},
                {11, 11, 31, 24},
                {5, 1, 6, 5},
                {-1, 2, 1, 1}
        };

      default:
        return new float[][]{
                {3, 12, 6, 15},
                {3, 14, 7, 19},
                {1, 9, 1, 0},
                {2, 8, 5, 7}
        };
    }
  }

  private boolean isEqual(SquareMatrix s, SquareMatrix m) {
    int size = s.size();
    if (m.size() != size) {
      return false;
    }

    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        if (s.get(i, j) != m.get(i, j)) {
          return false;
        }
      }
    }
    return true;
  }
}
