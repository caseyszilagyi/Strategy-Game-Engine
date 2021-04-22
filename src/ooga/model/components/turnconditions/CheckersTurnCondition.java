package ooga.model.components.turnconditions;

import ooga.model.components.movehistory.ActionType;
import ooga.model.components.movehistory.CompletedAction;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

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
