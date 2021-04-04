package ooga.model.game_components;

/**
 * Used to represent coordinate values. Overrides the equals and hashCode methods in order to be
 * able to compare and check whether coordinates are in a set, which is very helpful for testing
 *
 * @author Casey Szilagyi
 */
public class Coordinate {

  private int x;
  private int y;

  /**
   * Initializes this coordinate pair
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Sets the x coordinate
   *
   * @param x The x coordinate
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Sets the y coordinate
   *
   * @param y Theh y coordinate
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Gets the x coordinate
   *
   * @return The x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y coordinate
   *
   * @return The y coordinate
   */
  public int getY() {
    return y;
  }


  /**
   * This method is overridden so that two coordinates with equal values can be compared, and return
   * true if the coordinates are the same
   *
   * @param coordinates The other coordinate pair
   * @return True if the x and y are the same, false otherwise
   */
  @Override
  public boolean equals(Object coordinates) {
    if (coordinates == null) {
      return false;
    }
    if (coordinates == this) {
      return true;
    }
    if (!(coordinates instanceof Coordinate)) {
      return false;
    }

    Coordinate otherCoordinates = (Coordinate) coordinates;
    if (x == otherCoordinates.getX() && y == otherCoordinates.getY()) {
      return true;
    }
    return false;
  }

  /**
   * This method is overridden in order to be able to check if coordinates are in a set. Useful
   * mainly for testing
   *
   * @return The hashed value
   */
  @Override
  public int hashCode() {
    return x * 100 + y;
  }
}
