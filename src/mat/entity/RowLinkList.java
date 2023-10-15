package mat.entity;

/**
 * A class to extend the link list implementation to a column linked list.
 */
public class RowLinkList extends AbstarctLinkList {

  /**
   * Creates a new link list with a row type sentinel.
   */
  public RowLinkList() {
    super(new RowSentinel());
  }
}
