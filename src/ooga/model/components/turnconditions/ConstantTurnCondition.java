package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This class is used to determine if the turn is over for a specific game type, and is defined in the games .xml file
 *
 * This class is dependant on the TurnConditionResult enum.
 *
 * Code Example:
 *
 * //assumes GameBoard gameBoard and the most recent GamePiece gamePiece have been defined previously
 * TurnCondition turnCondition = new ConstantTurnCondition();
 * gameBoard.movePiece(0,0);
 * if(turnCondition.isTurnOver(gameBoard, gamePiece) == SWITCH){
 *   //this will never happen
 * }
 *
 * @author Cole Spector
 */
public class ConstantTurnCondition implements TurnCondition{

  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    return TurnConditionResult.STAY;
  }
}
