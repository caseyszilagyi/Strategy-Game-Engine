package ooga.model.game_components;

import java.util.List;

public class GameBoard implements Board {
  private List<GamePiece> activePieces;

  public List<Coordinate> getAllActivePieceCoordinates() {
    return null;
  }

  @Override
  public void movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {

  }

  @Override
  public void removePiece(Coordinate coordinate) {

  }

  @Override
  public void changePiece(Coordinate coordinate, GamePiece newPieceType) {

  }

  @Override
  public void addPiece(Coordinate coordinate, GamePiece newPieceType) {

  }

}
