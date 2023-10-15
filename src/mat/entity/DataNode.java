package mat.entity;

/**
 * A class to represent the data node type of object in the list which contains row, column and the
 * data values.
 */
public class DataNode extends Node {
  private final int row;

  private final int col;

  private float data;

  /**
   * Creates a new data node object.
   *
   * @param row  row number of the node in the matrix
   * @param col  col number of the node in the matrix
   * @param data value of the node in the matrix
   */
  public DataNode(int row, int col, float data) {
    this.row = row;
    this.col = col;
    this.data = data;
  }

  /**
   * Gets the row value of the node.
   *
   * @return row number
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column value of the node.
   *
   * @return column number
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the data in the node.
   *
   * @return data value
   */
  public float getData() {
    return data;
  }

  /**
   * Sets the data value of the node.
   */
  public void setData(float data) {
    this.data = data;
  }

  /**
   * Delete the given data node.
   */
  public void remove() {
    this.left.right = this.right;
    this.right.left = this.left;
    this.up.down = this.down;
    this.down.up = this.up;
  }
}
