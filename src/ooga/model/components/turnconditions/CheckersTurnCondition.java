package ooga.model.components.turnconditions;

import ooga.model.components.ActionType;
import ooga.model.components.CompletedAction;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class CheckersTurnCondition implements TurnCondition{

  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    CompletedAction lastMove = gameBoard.getMostRecentAction();
    if(lastMove.actionType() == ActionType.MOVE &&
    Math.abs(lastMove.coords()[1].getX() - lastMove.coords()[0].getX()) ==2){
      return TurnConditionResult.STAY;
    }
    return TurnConditionResult.SWITCH;
  }
}
