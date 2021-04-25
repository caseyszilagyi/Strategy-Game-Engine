package ooga.model.components.computer;

import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AI {

  private Coordinate startCoordinate;
  private Coordinate endCoordinate;

  public void determineMove(GameBoard board) {
    List<Coordinate> moves = new ArrayList<>();
    while (moves.size() == 0) {
      startCoordinate = chooseRandomPiece(board.getPieceCoordinateMap());
      GamePiece randomPiece = board.getPieceAtCoordinate(startCoordinate);
      if(randomPiece.getPieceTeam().equals("AI")) {
        moves = new ArrayList<>(randomPiece.determineAllLegalMoves());
      }
    }
    endCoordinate = moves.get((int) (Math.random() * moves.size()));
  }

  private Coordinate chooseRandomPiece(Map<Coordinate, GamePiece> pieceCoordinateMap) {
    List<Coordinate> coordinateList = new ArrayList<>(pieceCoordinateMap.keySet());
    int randomIndex = (int) (Math.random() * coordinateList.size());
    return coordinateList.get(randomIndex);
  }

  public List<Coordinate> getMove() {
    return List.of(startCoordinate, endCoordinate);
  }
}
