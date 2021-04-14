package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public interface TurnCondition {
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece);
}
