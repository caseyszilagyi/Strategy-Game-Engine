package ooga.model.components.computer;

import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class determines moves for the AI and also sends the move in the form of coordinates to the
 * GameEngine to execute the move
 *
 * @author Shaw Phillips
 */
public class AI {

  private Coordinate startCoordinate;
  private Coordinate endCoordinate;

  /**
   * Determines the move for the AI to make
   * @param board Takes in the GameBoard as a parameter to get all of the possible movements
   */
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

  /**
   * Sends starting and ending coordinates for the selected move
   * @return List of coordinates: (starting, ending);
   */
  public List<Coordinate> getMove() {
    return List.of(startCoordinate, endCoordinate);
  }
}
