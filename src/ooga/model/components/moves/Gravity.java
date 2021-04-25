package ooga.model.components.moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

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
