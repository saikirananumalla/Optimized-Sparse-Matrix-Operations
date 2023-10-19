package mat.entity;

/**
 * A Junit class to test row sentinel class by extending sentinel test abstract class.
 */
public class RowSentinelTest extends SentinelTest {

  @Override
  protected Sentinel getNewSentinel() {
    return new RowSentinel();
  }
}
