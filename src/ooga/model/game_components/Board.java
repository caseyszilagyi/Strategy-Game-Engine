package ooga.model.game_components;

import java.util.List;

public interface Board{

  public List<Coordinate> getAllActivePieceCoordinates();

  public void movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate);

  public void removePiece(Coordinate coordinate);

  public void changePiece(Coordinate coordinate, GamePiece newPieceType);

  public void addPiece(Coordinate coordinate, GamePiece newPieceType);

}
