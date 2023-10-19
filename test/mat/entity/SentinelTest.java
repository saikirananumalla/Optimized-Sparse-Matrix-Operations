package mat.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * An abstract JUnit test class to test the sentinel class.
 */
public abstract class SentinelTest {

  private Sentinel sentinel;

  @Before
  public void setUp() {
    sentinel = getNewSentinel();
    sentinel.add(0, new DataNode(0, 0, 1));
    sentinel.add(1, new DataNode(1, 1, 2));
    sentinel.add(2, new DataNode(2, 2, 3));
  }

  protected abstract Sentinel getNewSentinel();

  @Test
  public void get() {
    assertEquals(3.0, sentinel.get(2), 0.001);
  }

  @Test
  public void add() {
    sentinel.add(1, new DataNode(1, 1, 4));
    assertEquals(4.0, sentinel.get(1), 0.001);
  }

  @Test
  public void setIdentity() {
    sentinel.setIdentity(new DataNode(0, 0, 1));
    assertEquals(1, sentinel.get(0), 0.001);
    assertEquals(0, sentinel.get(1), 0.001);
    assertEquals(0, sentinel.get(2), 0.001);
  }

  @Test
  public void getIndices() {
    List<Pair> cols = sentinel.getIndices();

    assertEquals(0, cols.get(0).index);
    assertEquals(1, cols.get(0).value, 0.001);
    assertEquals(1, cols.get(1).index);
    assertEquals(2, cols.get(1).value, 0.001);
    assertEquals(2, cols.get(2).index);
    assertEquals(3, cols.get(2).value, 0.001);
  }
}