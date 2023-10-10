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
    if (other.getClass() == ArrayMatrix.class) {
      result = new ArrayMatrix(size);

      for (int i = 0; i < size; i += 1) {
        int a = 0;
        int c = size;
        List<Integer> cols = rows.get(i).getIndices();
        int b = 0;
        int d = cols.size();

        while (a < c && b < d) {
          if (a < cols.get(b)) {
            result.set(i, a, other.get(i, a));
            a++;
          } else if (a > cols.get(b)) {
            result.set(i, cols.get(b), getUtil(i, cols.get(b)));
            b++;
          } else {
            result.set(i, a, getUtil(i, a) + other.get(i, a));
            a++;
            b++;
          }
        }

        while(a < c){
          result.set(i, a, other.get(i, a));
          a++;
        }
      }
    }
    else {
      result = new SparseMatrix(size);
      SparseMatrix sm = (SparseMatrix) other;

      for (int i = 0; i < size; i += 1) {
        List<Integer> cols = rows.get(i).getIndices();
        List<Integer> cols1 = sm.rows.get(i).getIndices();
        int a = 0;
        int c = cols1.size();

        int b = 0;
        int d = cols.size();

        while (a < c && b < d) {
          Integer colA = cols1.get(a);
          Integer colB = cols.get(b);

          if (colA < colB) {
            result.set(i, colA, other.get(i, colA));
            a++;
          } else if (colA > colB) {
            result.set(i, colB, getUtil(i, colB));
            b++;
          } else {
            result.set(i, colA, getUtil(i, colA) + other.get(i, colA));
            a++;
            b++;
          }
        }

        while(a < c){
          result.set(i, cols1.get(a), other.get(i, cols1.get(a)));
          a++;
        }

        while(b < d){
          result.set(i, cols.get(b), other.get(i, cols.get(b)));
          b++;
        }
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

          int a = 0, b = 0;
          int c = row.size(), d = col.size();

          float sum = 0;

          while(a<c&&b<d){

            if (row.get(a) < col.get(b)){
              a++;
            }
            else if (row.get(a) > col.get(b)){
              b++;
            }
            else {
              sum+= (get(i, row.get(a)) * other.get(col.get(b), j));
              a++;
              b++;
            }
          }

          if(sum!=0) result.setUtil(i, j, sum);

        }
      }
    }
    else {

      for (int i = 0; i < this.size(); i += 1) {
        List<Integer> row = rows.get(i).getIndices();
        for (int j = 0; j < size; j++) {

          int a = 0, b = 0;
          int c = row.size(), d = size;

          float sum = 0;

          while(a<c && b<d){

            if (row.get(a) < b){
              a++;
            }
            else if (row.get(a) > b){
              b++;
            }
            else {
              sum+= (get(i, row.get(a)) * other.get(b, j));
              a++;
              b++;
            }
          }

          if(sum!=0) result.setUtil(i, j, sum);

        }
      }

    }
    return result;
  }

  public void print() {
    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        System.out.print(get(i, j) + "  ");
      }
      System.out.println("\n");
    }
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

}
