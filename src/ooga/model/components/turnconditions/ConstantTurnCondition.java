package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class ConstantTurnCondition implements TurnCondition{

  @Override
  public boolean isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    return false;
  }
}
