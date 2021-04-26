package ooga.model.components.turnconditions;

import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This class is used to determine if the turn is over for a specific game type, and is defined in the games .xml file
 * @author Cole Spector
 */
public interface TurnCondition {

  /**
   * This method is called to determine if the turn is over
   * @param gameBoard the GameBoard used in the current game
   * @param gamePiece the GamePiece which made the last move
   * @return the TurnConditionResult, either SWITCH, STAY, or PROMPT depending on the game scenario
   */
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece);
}
