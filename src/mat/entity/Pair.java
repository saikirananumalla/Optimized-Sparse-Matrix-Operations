package mat.entity;

/**
 * A class to store the matrix index and the corresponding value in a single object.
 */
public class Pair {
  public int index;

  public float value;

  /**
   * Creates a new pair object with given index and value.
   *
   * @param index index of matrix node
   * @param value data value of corresponding index
   */
  public Pair(int index, float value) {
    this.index = index;
    this.value = value;
  }
}
