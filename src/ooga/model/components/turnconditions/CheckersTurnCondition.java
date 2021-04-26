package ooga.model.components.turnconditions;

import ooga.model.components.movehistory.ActionType;
import ooga.model.components.movehistory.CompletedAction;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * This class is used to determine if the turn is over for a specific game type, and is defined in the games .xml file
 *
 *
 * This class is dependant on the TurnConditionResult enum.
 *
 *
 * Code Example:
 *
 * //assumes GameBoard gameBoard and the most recent GamePiece gamePiece have been defined previously
 * TurnCondition turnCondition = new CheckersTurnCondition();
 * gameBoard.movePiece(0,0);
 * if(turnCondition.isTurnOver(gameBoard, gamePiece) == SWITCH){
 *   //swap current user turn;
 * }
 *
 * @author Casey Szilagyi
 */
public class CheckersTurnCondition implements TurnCondition{

  @Override
  public TurnConditionResult isTurnOver(GameBoard gameBoard, GamePiece gamePiece) {
    CompletedAction lastMove = gameBoard.getMostRecentAction();
    if(lastMove.actionType() == ActionType.MOVE &&
        gamePiece.getPieceTeam().equals(lastMove.correspondingPiece().getPieceTeam()) &&
        Math.abs(lastMove.coords()[1].getX() - lastMove.coords()[0].getX()) ==2 &&
        gamePiece.hasTakeMove()){
      gameBoard.setIsHeldPiece(true);
      gameBoard.determineAllLegalTakeMoves(gamePiece.getPieceCoordinates().getX(), gamePiece.getPieceCoordinates()
          .getY());
      return TurnConditionResult.STAY;
    }
    return TurnConditionResult.SWITCH;
  }
}
