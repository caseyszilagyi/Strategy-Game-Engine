package ooga.model.game_components.move_types;

import java.util.List;
import java.util.Map;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

public class InfiniteJump extends PieceMovement {

  public InfiniteJump(Map<String, String> parameters){
    super(parameters);
  };


  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board) {
    return null;
  }
}
