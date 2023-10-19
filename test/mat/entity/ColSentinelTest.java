package mat.entity;

/**
 * A Junit class to test column sentinel class by extending sentinel test abstract class.
 */
public class ColSentinelTest extends SentinelTest {

  @Override
  protected Sentinel getNewSentinel() {
    return new ColSentinel();
  }
}
