package ooga.model.game_components;

import java.util.List;

public interface Board{

  public List<Coordinate> getAllActivePieceCoordinates();

  public void movePiece(GamePiece piece, Coordinate endingCoordinate);

  public void removePiece(GamePiece gamePiece);

  public void changePiece(GamePiece gamePiece, GamePiece newPieceType);

  public void addPiece(Coordinate coordinate, GamePiece newPieceType);
}
