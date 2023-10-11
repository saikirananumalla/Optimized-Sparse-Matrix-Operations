package mat;

import java.util.ArrayList;
import java.util.List;

import mat.entity.ColLinkList;
import mat.entity.DataNode;
import mat.entity.LinkList;
import mat.entity.Node;
import mat.entity.RowLinkList;

public class SparseMatrix extends AbstractSquareMatrix{

  private final List<LinkList> rows;

  private final List<LinkList> cols;

  public SparseMatrix(int size) {
    validateSize(size);

    rows = new ArrayList<>();
    cols = new ArrayList<>();
    this.size = size;

    for(int i=0;i<size;i++){
      rows.add(new RowLinkList());
      cols.add(new ColLinkList());
    }

  }

  @Override
  public void setIdentity() {
    for (int i = 0; i < size; i += 1) {
      DataNode d = new DataNode(i, i, 1);
      rows.get(i).setIdentity(d);
      cols.get(i).setIdentity(d);
      }
  }

  @Override
  public void set(int i, int j, float value) throws IllegalArgumentException {
    validateIndices(i, j);
    setUtil(i, j, value);
  }

  @Override
  public float get(int i, int j) throws IllegalArgumentException {
    validateIndices(i, j);
    return getUtil(i, j);
  }


  @Override
  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    SquareMatrix result;

    if (other.getClass() == SparseMatrix.class) {
      result = new SparseMatrix(size);
      SparseMatrix sm = (SparseMatrix) other;

      for (int i = 0; i < size; i += 1) {
        List<Integer> cols1 = rows.get(i).getIndices();
        List<Integer> cols2 = sm.rows.get(i).getIndices();
        addRows(other, cols2, cols1, result, i);
      }
    }

    else {
      result = new ArrayMatrix(size);

      for (int i = 0; i < size; i += 1) {
        List<Integer> cols = rows.get(i).getIndices();
        addRows(other, cols, null, result, i);
      }
    }
    return result;
  }



  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    validateEqualSize(other);
    SparseMatrix result = new SparseMatrix(size);

    if (other.getClass() == SparseMatrix.class) {
      SparseMatrix sm = (SparseMatrix) other;

      for (int i = 0; i < size; i += 1) {
        List<Integer> row = rows.get(i).getIndices();

        for (int j = 0; j < size; j+= 1) {
          List<Integer> col = sm.cols.get(j).getIndices();
          multiplyRowAndColumn(other, row, col, i, j, result);
        }
      }
    }

    else {

      for (int i = 0; i < this.size(); i += 1) {
        List<Integer> row = rows.get(i).getIndices();

        for (int j = 0; j < size; j++) {
          multiplyRowAndColumn(other, row, null, i, j, result);
        }
      }
    }
    return result;
  }

  private float getUtil(int i, int j) {
    return rows.get(i).get(j);
  }

  private void setUtil(int i, int j, float value) {
    if (getUtil(i, j) == 0){
      if (value == 0) return;
      Node n = new DataNode(i, j, value);
      rows.get(i).add(j, n);
      cols.get(j).add(i, n);
      return;
    }
    else if (value == 0){
      rows.get(i).remove(j);
    }

    rows.get(i).set(j, value);
  }

  private void addRows(SquareMatrix other, List<Integer> cols1, List<Integer> cols2,
                       SquareMatrix result, int row) {
    int a = 0;
    int c = cols1.size();

    int b = 0;
    int d = (cols2 == null ? size : cols2.size());

    while (a < c && b < d) {
      Integer colA = cols1.get(a);
      Integer colB = (cols2 == null ? b : cols2.get(b));

      if (colA < colB) {
        result.set(row, colA, other.get(row, colA));
        a++;
      } else if (colA > colB) {
        result.set(row, colB, getUtil(row, colB));
        b++;
      } else {
        result.set(row, colA, getUtil(row, colA) + other.get(row, colA));
        a++;
        b++;
      }
    }

    while(a < c){
      result.set(row, cols1.get(a), other.get(row, cols1.get(a)));
      a++;
    }

    while(b < d){
      int colB = (cols2 == null ? b : cols2.get(b));
      result.set(row, colB, other.get(row, colB));
      b++;
    }
  }

  private void multiplyRowAndColumn(SquareMatrix other, List<Integer> row, List<Integer> col,
                                    int rowNumber, int columnNumber, SparseMatrix result) {
    int a = 0;
    int b = 0;
    int c = row.size();
    int d = (col == null ? size : col.size());

    float sum = 0;

    while(a<c && b<d){

      int colA = row.get(a);
      int rowB = (col == null ? b : col.get(b));

      if (colA < rowB){
        a++;
      }
      else if (colA > rowB){
        b++;
      }
      else {
        sum+= (get(rowNumber, colA) * other.get(rowB, columnNumber));
        a++;
        b++;
      }
    }

    if(sum!=0) result.setUtil(rowNumber, columnNumber, sum);
  }

}
