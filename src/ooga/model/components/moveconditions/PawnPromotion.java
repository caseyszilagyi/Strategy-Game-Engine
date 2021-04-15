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
  private List<String> chessPieceList = new ArrayList<>(Arrays.asList("queen", "rook", "bishop", "knight"));

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param viewController The controller used to communicate with the front end
   * @param gameBoard      The board that the pieces are on.
   * @param parameters     The map with parameters, if there are any
   * @param piece          The piece that the restriction corresponds to
   */
  public PawnPromotion(FrontEndExternalAPI viewController,
      GameBoard gameBoard, Map<String, String> parameters, GamePiece piece, int direction) {
    super(viewController, gameBoard, parameters, piece, direction);
    this.gameBoard = gameBoard;
    this.viewController = viewController;
  }

  @Override
  public void executeCondition(Coordinate endingCoordinates) {
    int yPos = endingCoordinates.getY();
    if(yPos == gameBoard.getHeight()|| yPos == 0){
      viewController.givePieceChangeOptions(chessPieceList);
    }
  }
}
