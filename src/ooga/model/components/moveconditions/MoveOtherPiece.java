package ooga.model.components.moveconditions;

import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * Used to move another piece relative to the piece that was passed. Is executed after the
 * piece passed is moved
 *
 * @author Casey Szilagyi
 */
public class MoveOtherPiece extends Condition{

  private int relativeStartX;
  private int relativeStartY;
  private int relativeEndX;
  private int relativeEndY;
  private int pieceX;
  private int pieceY;
  private GameBoard gameBoard;

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param viewController The controller used to communicate with the front end
   * @param gameBoard      The board that the pieces are on.
   * @param parameters     Holds the relative starting x and y coordinates and the ending
   *                       relative x and y coordinates of the piece to move
   * @param piece          The piece that this condition corresponds to
   */
  public MoveOtherPiece(FrontEndExternalAPI viewController,
      GameBoard gameBoard,
      Map<String, String> parameters, GamePiece piece, int direction) {
    super(viewController, gameBoard, parameters, piece, direction);
    relativeStartX = Integer.parseInt(parameters.get("relativeStartX")) * direction;
    relativeStartY = Integer.parseInt(parameters.get("relativeStartY")) * direction;
    relativeEndX = Integer.parseInt(parameters.get("relativeEndX")) * direction;
    relativeEndY = Integer.parseInt(parameters.get("relativeEndY")) * direction;
    this.gameBoard = gameBoard;
  }

  /**
   * Moves a piece on the board, relative to our piece
   * @param endingCoordinates The ending coordinates of the piece that a piece is being moved
   *                          relative to
   */
  @Override
  public void executeCondition(Coordinate endingCoordinates) {
    pieceX = endingCoordinates.getX();
    pieceY = endingCoordinates.getY();
    gameBoard.movePiece(relativeStartX + pieceX, relativeStartY + pieceY, relativeEndX + pieceX, relativeEndY + pieceY);
  }
}
