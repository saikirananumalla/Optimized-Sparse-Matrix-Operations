package mat.entity;

/**
 * A class to extend the link list implementation to a column linked list that uses a column type
 * sentinel and links nodes in up and down fashion.
 */
public class ColLinkList extends AbstarctLinkList {

  /**
   * Creates a new link list with a column type sentinel.
   */
  public ColLinkList() {
    super(new ColSentinel());
  }
}
