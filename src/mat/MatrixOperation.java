package mat;

import java.util.List;

/**
 * An abstract class to implement matrix operations add, post multiplication and pre multiplication,
 * it subbranches into required methods of operation based on the subclass of the matrices given.
 */
public abstract class MatrixOperation {

  /**
   * Add the given two square matrices.
   *
   * @param squareMatrix1 square matrix 1
   * @param squareMatrix2 square matrix 2
   * @param res           result square matrix
   */
  public static void add(SquareMatrix squareMatrix1, SquareMatrix squareMatrix2,
                         SquareMatrix res) {
    if (squareMatrix1 instanceof ArrayMatrix) {
      ArrayMatrix arr = (ArrayMatrix) squareMatrix1;
      if (squareMatrix2 instanceof ArrayMatrix) {
        addUtil(arr, (ArrayMatrix) squareMatrix2, res);
      } else {
        addUtil(arr, (SparseMatrix) squareMatrix2, res);
      }
    } else if (squareMatrix1 instanceof SparseMatrix) {
      SparseMatrix spm = (SparseMatrix) squareMatrix1;
      if (squareMatrix2 instanceof ArrayMatrix) {
        addUtil((ArrayMatrix) squareMatrix2, spm, res);
      } else {
        addUtil(spm, (SparseMatrix) squareMatrix2, res);
      }
    }
  }


  /**
   * Post multiply the given two square matrices.
   *
   * @param squareMatrix1 square matrix 1
   * @param squareMatrix2 square matrix 2
   * @param res           result square matrix
   */
  public static void multiply(SquareMatrix squareMatrix1, SquareMatrix squareMatrix2,
                              SquareMatrix res) {
    if (squareMatrix1 instanceof ArrayMatrix) {
      ArrayMatrix arr = (ArrayMatrix) squareMatrix1;
      if (squareMatrix2 instanceof ArrayMatrix) {
        multiplyUtil(arr, (ArrayMatrix) squareMatrix2, res);
      } else {
        multiplyUtil(arr, (SparseMatrix) squareMatrix2, res);
      }
    } else if (squareMatrix1 instanceof SparseMatrix) {
      SparseMatrix spm = (SparseMatrix) squareMatrix1;
      if (squareMatrix2 instanceof ArrayMatrix) {
        multiplyUtil(spm, (ArrayMatrix) squareMatrix2, res);
      } else {
        multiplyUtil(spm, (SparseMatrix) squareMatrix2, res);
      }
    }
  }


  private static void addUtil(ArrayMatrix arrayMatrix, ArrayMatrix arrayMatrix1,
                              SquareMatrix result) {
    int size = arrayMatrix.size();

    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        result.set(i, j, arrayMatrix.get(i, j) + arrayMatrix1.get(i, j));
      }
    }
  }

  private static void addUtil(ArrayMatrix arrayMatrix, SparseMatrix sparseMatrix,
                              SquareMatrix result) {
    int size = sparseMatrix.size();

    for (int i = 0; i < size; ++i) {
      List<Integer> cols = sparseMatrix.getRows().get(i).getIndices();
      addRowsMixed(sparseMatrix, arrayMatrix, cols, result, i, size);
    }

  }

  private static void addUtil(SparseMatrix sparseMatrix1, SparseMatrix sparseMatrix2,
                              SquareMatrix result) {
    int size = sparseMatrix1.size();

    for (int i = 0; i < size; i++) {
      List<Integer> cols = sparseMatrix1.getRows().get(i).getIndices();
      List<Integer> otherCols = sparseMatrix2.getRows().get(i).getIndices();
      addRows(sparseMatrix1, sparseMatrix2, otherCols, cols, result, i);
    }
  }

  private static void multiplyUtil(ArrayMatrix arrayMatrix, ArrayMatrix arrayMatrix1,
                                   SquareMatrix result) {
    int size = arrayMatrix.size();

    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        float sum = 0;
        for (int k = 0; k < size; k += 1) {
          sum += arrayMatrix.get(i, k) * arrayMatrix1.get(k, j);
        }
        result.set(i, j, sum);
      }
    }
  }

  private static void multiplyUtil(ArrayMatrix arrayMatrix, SparseMatrix sparseMatrix,
                                   SquareMatrix result) {
    int size = arrayMatrix.size();

    for (int i = 0; i < size; ++i) {
      List<Integer> col = sparseMatrix.getCols().get(i).getIndices();

      for (int j = 0; j < size; ++j) {
        multiplyMixedType(arrayMatrix, sparseMatrix, col, j, i, result);
      }
    }
  }

  private static void multiplyUtil(SparseMatrix sparseMatrix, ArrayMatrix arrayMatrix,
                                   SquareMatrix result) {
    int size = arrayMatrix.size();

    for (int i = 0; i < size; ++i) {
      List<Integer> row = sparseMatrix.getRows().get(i).getIndices();

      for (int j = 0; j < size; ++j) {
        multiplyMixedType(sparseMatrix, arrayMatrix, row, i, j, result);
      }
    }
  }

  private static void multiplyUtil(SparseMatrix sparseMatrix, SparseMatrix sparseMatrix1,
                                   SquareMatrix result) {
    int size = sparseMatrix.size();

    for (int i = 0; i < size; i += 1) {
      List<Integer> row = sparseMatrix.getRows().get(i).getIndices();

      for (int j = 0; j < size; j += 1) {
        List<Integer> col = sparseMatrix1.getCols().get(j).getIndices();
        multiplyRowAndColumn(sparseMatrix, sparseMatrix1, row, col, i, j, result);
      }
    }
  }

  private static void addRows(SquareMatrix s, SquareMatrix m, List<Integer> mCols,
                              List<Integer> sCols, SquareMatrix result, int row) {
    int a = 0;
    int c = mCols.size();

    int b = 0;
    int d = sCols.size();

    while (a < c && b < d) {
      int colA = mCols.get(a);
      int colB = sCols.get(b);

      if (colA < colB) {
        result.set(row, colA, m.get(row, colA));
        a++;
      } else if (colA > colB) {
        result.set(row, colB, s.get(row, colB));
        b++;
      } else {
        result.set(row, colA, s.get(row, colA) + m.get(row, colA));
        a++;
        b++;
      }
    }

    while (a < c) {
      int colA = mCols.get(a);
      result.set(row, colA, m.get(row, colA));
      a++;
    }

    while (b < d) {
      int colB = sCols.get(b);
      result.set(row, colB, s.get(row, colB));
      b++;
    }
  }

  private static void addRowsMixed(SquareMatrix s, SquareMatrix a, List<Integer> cols,
                                   SquareMatrix result, int row, int size) {
    for (int i = 0; i < size; i++) {
      result.set(row, i, a.get(row, i));
    }

    for (int col : cols) {
      result.set(row, col, result.get(row, col) + s.get(row, col));
    }
  }

  private static void multiplyRowAndColumn(SquareMatrix s, SquareMatrix other, List<Integer> row,
                                           List<Integer> col, int rowNumber, int columnNumber,
                                           SquareMatrix result) {
    int a = 0;
    int b = 0;
    int c = row.size();
    int d = col.size();

    float sum = 0;

    while (a < c && b < d) {

      int colA = row.get(a);
      int rowB = col.get(b);

      if (colA < rowB) {
        a++;
      } else if (colA > rowB) {
        b++;
      } else {
        sum += (s.get(rowNumber, colA) * other.get(colA, columnNumber));
        a++;
        b++;
      }
    }

    if (sum != 0) {
      result.set(rowNumber, columnNumber, sum);
    }
  }

  private static void multiplyMixedType(SquareMatrix s, SquareMatrix other, List<Integer> row,
                                        int rowNumber, int columnNumber, SquareMatrix result) {

    float sum = 0;

    for (Integer a : row) {
      sum += (s.get(rowNumber, a) * other.get(a, columnNumber));
    }

    if (sum != 0) {
      result.set(rowNumber, columnNumber, sum);
    }
  }

}
