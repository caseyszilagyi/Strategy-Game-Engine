package ooga.model.game_components;

import java.util.List;

public class GameBoard implements Board {
  private List<GamePiece> activePieces;
  private GamePiece[][] gameBoard;
  public List<Coordinate> getAllActivePieceCoordinates() {
    return null;
  }

  public void movePiece(GamePiece piece, Coordinate endingCoordinate) {

  }

  public void removePiece(GamePiece gamePiece) {

  }

  public void changePiece(GamePiece gamePiece, GamePiece newPieceType) {

  }

  public void addPiece(Coordinate coordinate, GamePiece newPieceType) {

  }
}
