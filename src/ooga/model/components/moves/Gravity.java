package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This class represents a piece that falls to the lowest possible y-coordinate vacant on the GameBoard, while maintaining its x-coordinate.
 *
 * This class should be used via reflection, and should be defined in the GamePiece's .xml file in the
 * nested parameters:
 * <moves>
 *     <Gravity>
 *     </Gravity>
 * </moves>
 *
 * @author Cole Spector
 */
public class Gravity extends PieceMovement{

  private GameBoard gameBoard;
  private GamePiece currentPiece;

  /**
   * The constructor takes the parameters of the move. This includes the change in position of the
   * move, as well as the information about whether this move can take a piece
   *
   * @param parameters The map of parameters
   * @param direction  The multiplier used to change the direction that the piece uses
   * @param gameBoard  The board that the piece moves on
   */
  public Gravity(Map<String, String> parameters, int direction,
      GameBoard gameBoard, GamePiece piece) {
    super(parameters, direction, gameBoard);
    this.gameBoard = gameBoard;
    currentPiece = piece;
  }

  /**
   * Gets the single move associated with this Gravity movement object. Will be a single
   * set of a single coordinate or no coordinates.
   *
   * @param coordinates The coordinates of the piece that this move is acting on
   * @param pieceTeam    The name of the team of this piece
   * @return A list of the coordinates of possible moves
   */
  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, String pieceTeam) {
    int file = coordinates.getX();
    Coordinate coordinateToCheck;
    for(int y = gameBoard.getHeight() - 1; y >= 0; y--){
      coordinateToCheck = new Coordinate(file, y);
      if(!gameBoard.isPieceAtCoordinate(coordinateToCheck) || gameBoard.getPieceAtCoordinate(coordinateToCheck).equals(currentPiece)){
        return Arrays.asList(coordinateToCheck);
      }
    }
    return new ArrayList<>();
  }
}
