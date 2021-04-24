package ooga.model.components.moveconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.initialization.pieces.PieceCreator;


/**
 * Converts the checker to a king when it makes it to the last row
 *
 * @author Casey Szilagyi
 */
public class CheckerPromotion extends Condition {

  private GameBoard gameBoard;
  private GamePiece normalChecker;
  private PieceCreator pieceCreator;
  private int direction;

  /**
   *
   * @param gameBoard The board that the checker is on
   * @param parameters None, for this condition
   * @param piece The checker in question
   * @param direction The direction that corresponds to whether this player is on top/bottom
   */
  public CheckerPromotion(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece,
      int direction) {
    this.gameBoard = gameBoard;
    pieceCreator = new PieceCreator("checkers", gameBoard);
    this.direction = direction;
    normalChecker = piece;
  }

  @Override
  public void executeCondition(Coordinate endingCoordinates) {
    int yPos = endingCoordinates.getY();
    if (yPos == gameBoard.getHeight()-1 || yPos == 0) {
      gameBoard.removePiece(endingCoordinates);
      gameBoard.addPiece(pieceCreator.makePiece("kingChecker", endingCoordinates, direction, normalChecker.getPieceTeam()));
    }
  }
}
