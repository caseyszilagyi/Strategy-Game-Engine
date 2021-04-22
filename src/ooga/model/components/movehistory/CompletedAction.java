package ooga.model.components.movehistory;

import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

public record CompletedAction(int turnNumber, ActionType actionType, GamePiece correspondingPiece, Coordinate... coords) {

  public void revert(GameBoard board){
    switch(actionType()){
      case ADD:
        board.removePiece(coords()[0]);
        break;
      case REMOVE:
        board.addPiece(correspondingPiece());
        break;
      case MOVE:
        board.movePiece(coords()[1], coords()[0]);
        break;
    }
  }

}
