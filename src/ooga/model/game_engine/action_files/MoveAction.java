package ooga.model.game_engine.action_files;

import java.util.List;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;

public class MoveAction extends Action{

  private static final int PARAM_ONE_INDEX = 0;
  private static final int PARAM_TWO_INDEX = 1;

  Coordinate paramOne, paramTwo;

  /**
   * expects the List to be of size 2, with each index containing a String representing a coordinate,
   * in the form x:y, where x and y can be cast to an int.
   */

  public MoveAction(List<String> parameters) {
    if(parameters.size() != 2){
      System.err.println("Invalid parameters for MoveAction");
      //TODO: throw an error
    }
    paramOne = super.stringToCoordinate(parameters.get(PARAM_ONE_INDEX));
    paramTwo = super.stringToCoordinate(parameters.get(PARAM_TWO_INDEX));
  }

  @Override
  public boolean executeAction(GameBoard board, GameRules rules) {
    return board.movePiece(paramOne, paramTwo);
    //TODO: check rules for extraneous actions to be done on top of the movement
  }
}