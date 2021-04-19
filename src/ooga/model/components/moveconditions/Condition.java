package ooga.model.components.moveconditions;

import ooga.model.components.Coordinate;

/**
 * Meant to be used as a component of a piece movement object. Gives the movement a condition that
 * is executed if true.
 *
 * @author Casey Szilagyi
 */
public abstract class Condition {

  public abstract void executeCondition(Coordinate endingCoordinates);

}
