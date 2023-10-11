package mat;

public abstract class AbstractSquareMatrix implements SquareMatrix {

  protected int size;

  @Override
  public int size() {
    return size;
  }

  //todo - remove later
  public void print() {
    for (int i = 0; i < size; i += 1) {
      for (int j = 0; j < size; j += 1) {
        System.out.print(get(i, j) + "  ");
      }
      System.out.println("\n");
    }
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    return other.postmul(this);
  }

  protected static void validateSize(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("The size of an array matrix cannot be non-positive");
    }
  }

  protected void validateEqualSize(SquareMatrix other) {
    if (this.size() != other.size()) {
      throw new IllegalArgumentException("The dimensions of the two matrices do not match");
    }
  }

  protected void validateIndices(int i, int j) {
    if ((i < 0) || (i >= size) || (j < 0) || (j >= size)) {
      throw new IllegalArgumentException("Index cannot be outside 0 and size");
    }
  }


}
