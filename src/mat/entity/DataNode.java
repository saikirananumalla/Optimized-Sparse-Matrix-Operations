package mat.entity;

public class DataNode extends Node{
  private final int row;

  private final int col;

  private float data;

  public DataNode(int row, int col, float data) {
    this.row = row;
    this.col = col;
    this.data = data;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public float getData() {
    return data;
  }

  public void setData(float data) {
    this.data = data;
  }

  public void remove() {
    this.left.right = this.right;
    this.right.left = this.left;
    this.up.down = this.down;
    this.down.up = this.up;
  }
}
