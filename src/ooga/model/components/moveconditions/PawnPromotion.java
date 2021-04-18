package ooga.model.components.moveconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class PawnPromotion extends Condition {

  private GameBoard gameBoard;
  private FrontEndExternalAPI viewController;


  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param gameBoard  The board that the pieces are on.
   * @param parameters The map with parameters, if there are any
   * @param piece      The piece that the restriction corresponds to
   */
  public PawnPromotion(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece,
      int direction) {
    this.gameBoard = gameBoard;
  }

  @Override
  public void executeCondition(Coordinate endingCoordinates) {
    int yPos = endingCoordinates.getY();
    if (yPos == gameBoard.getHeight() || yPos == 0) {
      gameBoard.passPieceChangeOptions();
    }
  }
}
