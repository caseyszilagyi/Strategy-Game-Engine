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
    List<Coordinate> allMoves = new ArrayList<>();
    while (allMoves.size() == 0) {
      startCoordinate = chooseRandomPiece(board.getPieceCoordinateMap());
      GamePiece randomPiece = board.getPieceAtCoordinate(startCoordinate);
      if (randomPiece.getPieceTeam().equals("opponent")) {
        allMoves = new ArrayList<>(randomPiece.determineAllLegalMoves());
      }
    }
    List<Coordinate> takingMoves = new ArrayList<>(takeablePieces(allMoves, getUserPieces(board.getPieceCoordinateMap())));
    if (takingMoves.size() == 0) {
      endCoordinate = allMoves.get((int) (Math.random() * allMoves.size()));
    } else {
      endCoordinate = takingMoves.get((int) (Math.random() * takingMoves.size()));
    }
  }

  private List<Coordinate> takeablePieces(List<Coordinate> moves, List<Coordinate> userPieces) {
    List<Coordinate> takingMoves = new ArrayList<>();
    for (Coordinate coord : moves) {
      if (userPieces.contains(coord)) {
        takingMoves.add(coord);
      }
    }
    return takingMoves;
  }

  private List<Coordinate> getUserPieces(Map<Coordinate, GamePiece> pieceMap) {
    List<Coordinate> userPieceCoords = new ArrayList<>();
    for (GamePiece piece : pieceMap.values()) {
      if (piece.getPieceTeam().equals("user")) {
        userPieceCoords.add(piece.getPieceCoordinates());
      }
    }
    return userPieceCoords;
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
