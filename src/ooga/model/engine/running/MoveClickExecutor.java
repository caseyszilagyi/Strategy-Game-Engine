package ooga.model.engine.running;

import ooga.model.components.GameBoard;

/**
 * Meant to process clicks from the front end and execute the proper actions on the board
 *
 * @author Cole Spector
 * @author Casey Szilagyi
 */
public class MoveClickExecutor extends ClickExecutor {


  /**
   * Executes a click based on the position of the click and the name of the player
   * who's turn it is
   * @param xClickPosition The x coordinate of the click
   * @param yClickPosition The y coordinate of the click
   * @param currentPlayerTurn The string representing the current player's turn
   * @return True if a move was executed, false otherwise
   */
  @Override
  protected boolean executeClick(int xClickPosition, int yClickPosition, String currentPlayerTurn) {
    if (curBoard.getIsHeldPiece()) {
      return executePieceHoldingClick(xClickPosition, yClickPosition);
    }
    return executeNonPieceHoldingClick(xClickPosition, yClickPosition, currentPlayerTurn);
  }

  // Executes a click when a piece is currently being held
  private boolean executePieceHoldingClick(int xClickPosition, int yClickPosition) {
    curBoard.setIsHeldPiece(false);
    if (curBoard.isLegalMoveLocation(xClickPosition, yClickPosition)) {
      curBoard.movePiece(xClickPosition, yClickPosition);
      return true;
    } else {
      return false;
    }
  }

  // Executes a click when a piece is not currently being held
  private boolean executeNonPieceHoldingClick(int xClickPosition, int yClickPosition, String currentPlayerTurn) {
    if (curBoard.isPieceAtCoordinate(xClickPosition, yClickPosition) && checkProperTeamTurn(
        xClickPosition, yClickPosition, currentPlayerTurn)) {
      curBoard.determineAllLegalMoves(xClickPosition, yClickPosition);
      curBoard.setIsHeldPiece(true);
      return false;
    } else {
      return false;
    }
  }



}

