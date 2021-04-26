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
 * Sends a list of possible pieces to the front end when the pawn reaches the last rank. In the
 * current implementation, a queen is put in place of the pawn
 *
 * @author Casey Szilagyi
 */
public class PawnPromotion extends Condition {

  private GameBoard gameBoard;
  private FrontEndExternalAPI viewController;
  private List<String> chessPieceList = new ArrayList<>(
      Arrays.asList("queen", "rook", "bishop", "knight"));
  private PieceCreator pieceCreator;
  private int direction;
  private String pieceTeam;

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param gameBoard  The board that the pieces are on.
   * @param parameters The map with parameters, if there are any
   * @param piece      The piece that the restriction corresponds to
   * @param direction  The direction multiplier used for the piece movement
   */
  public PawnPromotion(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece,
      int direction) {
    this.gameBoard = gameBoard;
    pieceCreator = new PieceCreator("chess", gameBoard);
    this.direction = direction;
    pieceTeam = piece.getPieceTeam();
  }


  /**
   * Changes the pawn to a queen, and sends a list of possible changes to the front end
   *
   * @param endingCoordinates The ending coordinates of the move
   */
  @Override
  public void executeCondition(Coordinate endingCoordinates) {
    int yPos = endingCoordinates.getY();
    if (yPos == gameBoard.getHeight() - 1 || yPos == 0) {
      gameBoard.passPieceChangeOptions(chessPieceList);
      gameBoard.removePiece(endingCoordinates);
      gameBoard.addPiece(pieceCreator.makePiece("queen", endingCoordinates, direction, pieceTeam));
    }
  }
}
