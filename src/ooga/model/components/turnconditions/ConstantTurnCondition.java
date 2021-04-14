package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class ConstantTurnCondition implements TurnCondition{

  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    return TurnConditionResult.STAY;
  }
}