package ooga.model.components.moverestrictions;

import java.util.Map;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This restriction is used to make sure a move is only valid if it is a piece's first move
 *
 * @author Casey Szilagyi
 */
public class FirstMove extends Restriction {

  GamePiece piece;

  /**
   * Gives all the components that may be used for a restriction
   *
   * @param gameBoard  The board that the piece is on
   * @param parameters Not used for this restriction
   * @param piece      The piece in question
   */
  public FirstMove(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece) {
    this.piece = piece;
  }

  /**
   * Checks if the piece has moved already. If it has, then the piece movement is invalid
   *
   * @param endingCoordinates The final coordinates of the move
   * @return True if valid, false if not
   */
  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    if (piece.getLocationHistory().size() == 1) {
      return true;
    }
    return false;
  }
}
