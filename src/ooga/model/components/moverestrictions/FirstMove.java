package ooga.model.components.moverestrictions;

import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This restriction is used to make sure a pawn can only move forward 2 when it is it's first move
 *
 * @author Casey Szilagyi
 */
public class PawnFirstMove extends Restriction{

  GamePiece pawn;

  /**
   * Gives all the components that may be used for a restriction
   * @param viewController Not used
   * @param gameBoard The board that the pawn is on
   * @param parameters Not used
   * @param piece The pawn in question
   */
  public PawnFirstMove(
      FrontEndExternalAPI viewController, GameBoard gameBoard, Map<String, String> parameters, GamePiece piece){
    super(viewController, gameBoard, parameters, piece);
    pawn = piece;
  }

  /**
   * Checks if the pawn has moved already. If it has, then the 2 forward move is invaliid
   * @param endingCoordinates The final coordinates of the move
   * @return True if valid, falses if not
   */
  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    if(pawn.getLocationHistory().size() == 1){
      return true;
    }
    return false;
  }
}