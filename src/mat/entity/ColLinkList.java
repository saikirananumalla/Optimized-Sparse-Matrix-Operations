package mat.entity;

/**
 * A class to extend the link list implementation to a column linked list.
 */
public class ColLinkList extends AbstarctLinkList {

  /**
   * Creates a new link list with a column type sentinel.
   */
  public ColLinkList() {
    super(new ColSentinel());
  }
}
