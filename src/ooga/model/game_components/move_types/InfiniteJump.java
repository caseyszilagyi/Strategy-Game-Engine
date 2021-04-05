package ooga.model.game_components.move_types;

import java.util.List;
import java.util.Map;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;

public class InfiniteJump extends PieceMovement {

  public InfiniteJump(Map<String, String> parameters, int direction){
    super(parameters, direction);
  };


  @Override
  public List<Coordinate> getAllPossibleMoves(Coordinate coordinates, GameBoard board, String teamName) {
    return null;
  }
}
