package ooga.model.game_components;

/**
 * Represents a generic game piece. Has a location and an indicator representing whether
 * it exists on the board or not
 */
public abstract class Piece {

  int row;
  int col;
  boolean isAlive = true;

  public Piece(int r, int c){
    row = r;
    col = c;
  }

}

