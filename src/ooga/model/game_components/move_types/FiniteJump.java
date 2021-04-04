package ooga.model.game_components.move_types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

public class FiniteJump extends PieceMovement {

  public FiniteJump(Map<String, String> parameters){
    super(parameters);
  }

  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board) {
    if(checkIfMoveInBounds(coordinates) && checkIfPieceInTakePosition(coordinates)){
      List<Coordinate> possibleMoves = new ArrayList<Coordinate>();
      Coordinate result = new Coordinate(coordinates.getX() + getChangeX(),coordinates.getY() + getChangeX());
      possibleMoves.add(result);
      return possibleMoves;
    }
    return new ArrayList<>();
  }
}
