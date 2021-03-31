package ooga.model.game_components;

/**
 * Represents a generic game piece. Has a location and an indicator representing whether
 * it exists on the board or not
 */
public abstract class GamePiece {

  int row;
  int col;
  boolean isAlive = true;

  public GamePiece(int r, int c){
    row = r;
    col = c;
  }

}

