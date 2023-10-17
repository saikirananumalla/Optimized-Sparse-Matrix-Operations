package mat.entity;

/**
 * A class to extend the link list implementation to a row linked list that uses a row type
 * sentinel and links data nodes in left and right fashion.
 */
public class RowLinkList extends AbstarctLinkList {

  /**
   * Creates a new link list with a row type sentinel.
   */
  public RowLinkList() {
    super(new RowSentinel());
  }
}
