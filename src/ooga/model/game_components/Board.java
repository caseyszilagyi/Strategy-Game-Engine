package ooga.model.game_components;

import java.util.List;

public interface Board{

  public List<Coordinate> getAllActivePieceCoordinates();

  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate);

  public boolean removePiece(Coordinate coordinate);

  public boolean changePiece(Coordinate coordinate, GamePiece newPieceType);

  public boolean addPiece(GamePiece newPieceType);

}
