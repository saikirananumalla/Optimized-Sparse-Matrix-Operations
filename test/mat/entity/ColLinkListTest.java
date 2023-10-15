package mat.entity;

/**
 * A Junit class to test the column linked list by extending its abstract tests.
 */
public class ColLinkListTest extends AbstractLinkListTest {

  @Override
  protected LinkList getNewLinkList() {
    return new ColLinkList();
  }
}
