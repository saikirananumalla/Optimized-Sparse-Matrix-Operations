package mat;

public class Main {
  public static void main(String... args){
//    SparseMatrix mat = new SparseMatrix(3);
//    //mat.print();
//    SquareMatrix sm = mat;
//    sm.set(1,1, 2f);
//    sm.set(2,2,2f);
//    //mat.print();
//
//    SquareMatrix one = new ArrayMatrix(4);
//    float [][]onevalues = {
//            {1,2,3,4},
//            {1,5,2,1},
//            {0,1,-1,2},
//            {0,0,1,0}};
//    for (int i=0;i<4;i+=1) {
//      for (int j=0;j<4;j+=1) {
//        one.set(i,j,onevalues[i][j]);
//      }
//    }
//    one.print();

//    System.out.println();
//    System.out.println();

    SparseMatrix m2 = new SparseMatrix(4);
    m2.set(0,1, 2f);
    m2.set(1,2,2);
    m2.set(3,1,2);
    m2.set(2,0,2);
    m2.set(1,3,0);
    m2.setIdentity();
    m2.print();

    System.out.println();
    System.out.println();

    SparseMatrix m3 = new SparseMatrix(4);
    m3.set(0,1, 2f);
    m3.set(0, 2, 2);
    m3.set(1,2,2);
    m3.set(3,1,2);
    m3.set(3, 0, 2);
    m3.set(2,0,2);
    m3.set(2,3,2);
    m3.set(1,3,2);
    m3.setIdentity();
    m3.print();

    System.out.println();
    System.out.println();
    System.out.println();

    m2.postmul(m3).print();


  }
}
