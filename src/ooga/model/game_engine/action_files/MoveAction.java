package ooga.model.game_engine.action_files;

import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;

public class MoveAction extends Action{

  Coordinate paramOne, paramTwo;

  public MoveAction(String parameterOne, String parameterTwo) {
    paramOne = stringToCoordinate(parameterOne);
    paramTwo = stringToCoordinate(parameterTwo);
  }

  @Override
  public void executeAction(GameBoard board, GameRules rules) {
    board.movePiece(paramOne, paramTwo);
    //TODO: check rules for extraneous actions to be done on top of the movement
  }
}
