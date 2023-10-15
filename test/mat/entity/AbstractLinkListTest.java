package mat.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * A Junit class to abstract the testing of linked list implementation.
 */
public abstract class AbstractLinkListTest {
  private LinkList list;

  @Before
  public void setUp() {
    list = getNewLinkList();
    list.add(1, new DataNode(1, 1, 2));
    list.add(3, new DataNode(3, 3, 8));
    list.add(2, new DataNode(2, 2, 6));
  }

  protected abstract LinkList getNewLinkList();

  @Test
  public void testAdd() {
    list.add(0, new DataNode(0, 0, 4));
    assertArrayEquals(new Object[]{0, 1, 2, 3}, list.getIndices().toArray());
  }

  @Test
  public void testGet() {
    assertEquals(8, list.get(3), 0.01);
  }

  @Test
  public void testSetIdentity() {
    list.setIdentity(new DataNode(0, 0, 1));
    assertArrayEquals(new Object[]{0}, list.getIndices().toArray());
  }

}
