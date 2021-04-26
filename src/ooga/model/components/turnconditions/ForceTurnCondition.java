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
 * TurnCondition turnCondition = new ForceTurnCondition();
 * gameBoard.movePiece(0,0);
 * if(turnCondition.isTurnOver(gameBoard, gamePiece) == SWITCH){
 *   //this case will always run
 *   //swap current user turn;
 * }
 *
 * @author Cole Spector
 */
public class ForceTurnCondition implements TurnCondition{

  /**
   * This method is called to determine if the turn is over
   * @param gameBoard the GameBoard used in the current game
   * @param gamePiece the GamePiece which made the last move
   * @return the TurnConditionResult, either SWITCH, STAY, or PROMPT depending on the game scenario
   */
  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    return TurnConditionResult.SWITCH;
  }
}
