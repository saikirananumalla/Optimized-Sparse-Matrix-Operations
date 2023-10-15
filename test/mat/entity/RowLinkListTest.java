package mat.entity;

/**
 * A Junit class to test the row linked list by extending its abstract tests.
 */
public class RowLinkListTest extends AbstractLinkListTest {
  @Override
  protected LinkList getNewLinkList() {
    return new RowLinkList();
  }
}
