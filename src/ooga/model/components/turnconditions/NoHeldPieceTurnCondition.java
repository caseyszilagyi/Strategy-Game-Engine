package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public class NoHeldPieceTurnCondition implements TurnCondition{

  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    if(gameBoard.getIsHeldPiece()){
      return TurnConditionResult.STAY;
    }
    return TurnConditionResult.SWITCH;

  }

}
