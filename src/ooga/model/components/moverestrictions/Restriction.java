package ooga.model.components.moverestrictions;

import ooga.model.components.Coordinate;

/**
 * Meant to be used as a component of a piece movement object, gives the movement some type
 * of restriction that alters how it is able to move
 *
 * @author Casey Szilagyi
 */
public abstract class Restriction {

  /**
   * Checks the given restriction. If the restriction is not violated, it will return true
   * @param endingCoordinates The ending coordinates of the move
   * @return True if the restriction is not violated, false otherwise
   */
  public abstract boolean checkRestriction(Coordinate endingCoordinates);

}
