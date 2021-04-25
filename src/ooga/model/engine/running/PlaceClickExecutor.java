package ooga.model.engine.running;

import ooga.model.components.Coordinate;
import ooga.model.components.GamePiece;
import ooga.model.initialization.pieces.PieceCreator;

public class PlaceClickExecutor extends ClickExecutor{

  @Override
  protected boolean executeClick(int xClickPosition, int yClickPosition, String currentPlayerTurn) {
    if(curBoard.isPieceAtCoordinate(xClickPosition,yClickPosition)){
      return false;
    } else {
      GamePiece newPiece = getPieceToAdd(xClickPosition,yClickPosition,currentPlayerTurn);
      curBoard.addPiece(newPiece);
      return true;
    }
  }


  private GamePiece getPieceToAdd(int x, int y, String currentPlayerTurn){
    PieceCreator pieceCreator = new PieceCreator(curRules.getGameName(), curBoard);
    Coordinate coord = new Coordinate(x,y);
    return pieceCreator.makePiece(curRules.getAddPieceType(), coord, 1, currentPlayerTurn);
  }
}
