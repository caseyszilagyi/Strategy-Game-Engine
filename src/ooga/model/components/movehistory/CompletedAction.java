package ooga.model.components.movehistory;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public record CompletedAction(int turnNumber, ActionType actionType, GamePiece correspondingPiece, Coordinate... coords) {

  public void revert(GameBoard board, FrontEndExternalAPI viewController){
    switch(actionType()){
      case ADD:
        board.removeBackendPiece(coords()[0]);
        viewController.removePiece(coords()[0].getX(), coords()[0].getY());
        break;
      case REMOVE:
        board.addBackendPiece(correspondingPiece());
        viewController.setBoardSpace(coords()[0].getX(), coords()[0].getY(), correspondingPiece().getPieceName(),
            correspondingPiece().getPieceTeam());
        break;
      case MOVE:
        board.moveBackendPiece(coords()[1], coords()[0]);
        viewController.movePiece(coords()[1].getX(), coords()[1].getY(), coords()[0].getX(), coords()[0].getY());
        break;
    }
  }

}
