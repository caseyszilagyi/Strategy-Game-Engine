package ooga.model.game_components;

import java.util.List;

/**
 * Represents a generic game piece. Has a location and an indicator representing whether
 * it exists on the board or not
 */
public abstract class GamePiece() {

  //Global variables, initialized in constructer
  private Coordinate myCoordinates;
  private GameBoard myGameBoard;

  //returns true if move is possible, false if not
  public abstract boolean canMove(int newX, int newY);

  public List<Coordinate> getAllPossibleMoves(Coordinate pieceCoor) {
    return null;
  }


  public double getXPosition(){
    return myCoordinates.getX();
  }

  public double getYPosition(){
    return myCoordinates.getY();
  }
}

