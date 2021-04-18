package ooga.model.components;

public interface Board{


  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate);

  public void removePiece(Coordinate coordinate);

  public boolean addPiece(GamePiece newPieceType);

}
