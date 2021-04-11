package ooga.model.game_components;

import java.util.List;
import java.util.Set;

public interface Board{


  public boolean movePiece(Coordinate startingCoordinate, Coordinate endingCoordinate);

  public boolean removePiece(Coordinate coordinate);

  public boolean changePiece(Coordinate coordinate, GamePiece newPieceType);

  public boolean addPiece(GamePiece newPieceType);

}
