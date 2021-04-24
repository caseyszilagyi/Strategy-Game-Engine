package ooga.model.engine.running;

import ooga.model.components.GameBoard;

/**
 * Meant to process clicks from the front end and execute the proper actions on the board
 *
 * @author Casey Szilagyi
 */
public abstract class ClickExecutor {

  protected GameBoard curBoard;
  protected Boolean noTurnRules = true;

  /**
   * Executes a click based on the position of the click and the name of the player
   * who's turn it is
   * @param xClickPosition The x coordinate of the click
   * @param yClickPosition The y coordinate of the click
   * @param currentPlayerTurn The string representing the current player's turn
   * @return True if a move was executed, false otherwise
   */
  protected abstract boolean executeClick(int xClickPosition, int yClickPosition, String currentPlayerTurn);

  // Executes a click when a piece is currently being held


  protected boolean checkProperTeamTurn(int x, int y, String currentPlayerTurn) {
    return noTurnRules || curBoard.getPieceAtCoordinate(x, y).getPieceTeam()
        .equals(currentPlayerTurn);
  }

  /**
   * Sets whether turn rules are currently being followed
   * @param rules False if they are, true if they are not
   */
  protected void setIfTurnRules(Boolean rules){
    noTurnRules = rules;
  }


  /**
   * Sets the board being used to execute clicks
   * @param board The GameBoard object
   */
  protected void setBoard(GameBoard board) {
    curBoard = board;
  }


}
