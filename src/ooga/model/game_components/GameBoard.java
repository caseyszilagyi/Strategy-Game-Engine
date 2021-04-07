package ooga.model.game_components;

import java.util.List;

public class GameBoard implements Board {
  private List<GamePiece> activePieces;
  private Player currentTurn;

  public List<Coordinate> getAllActivePieceCoordinates() {
    return null;
  }

  @Override
  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate) {
    return false;
  }

  @Override
  public boolean removePiece(Coordinate coordinate) {
    return false;
  }

  @Override
  public boolean changePiece(Coordinate coordinate, GamePiece newPieceType) {
    return false;
  }

  @Override
  public boolean addPiece(GamePiece newPieceType) {
    return false;
  }

  public GamePiece getPieceAtCoordinate(Coordinate coordinate){
    return null;
  }

}
