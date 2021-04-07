package ooga.model.game_components;

import java.util.List;

public class GameBoard implements Board {
  private List<GamePiece> activePieces;

  public List<Coordinate> getAllActivePieceCoordinates() {
    return null;
  }

  @Override
  public void movePiece(GamePiece piece, Coordinate endingCoordinate) {
  }

  @Override
  public void removePiece(GamePiece gamePiece) {

  }

  @Override
  public void changePiece(GamePiece gamePiece, GamePiece newPieceType) {

  }

  @Override
  public void addPiece(Coordinate coordinate, GamePiece newPieceType) {

  }

}
