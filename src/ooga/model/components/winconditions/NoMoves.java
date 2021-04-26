package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;


/**
 * This class is used to determine if someone has won the current game, by checking if the opponent
 * team ahs any possible moves
 *
 * This class assumes a defined FrontEndExternalAPI is passed in, as well as the current GameBoard
 *
 * This class depends upon FrontEndExternalAPI.java and GameBoard.java
 *
 * Code Example:
 * //assumes GameBoard gameBoard and FrontEndExternalAPI frontEndExternalAPI have been previously defined
 *  EndGameCondition endGameCondition = new NoMoves(frontEndExternalAPI, gameBoard);
 *  gameBoard.movePiece(0,0);
 *  if(endGameCondition.checkForWin){
 *    exit(0);
 *  }
 *  //continue the game
 *
 * @author Casey Szilagyi
 */
public class NoMoves implements EndGameConditioin {

  private FrontEndExternalAPI viewController;
  private GameBoard board;

  /**
   * Initializer for the FourInARow EndGameCondition
   * @param viewController a valid FrontEndExternalAPI which the game is linked to.
   * @param board the current GameBoard to be used
   */
  public NoMoves(FrontEndExternalAPI viewController, GameBoard board){
    this.viewController = viewController;
    this.board = board;
  }

  /**
   * This method is called to determine if someone has won the current game
   * @param teamName the team name to check to see if they won
   * @return a boolean for whether or not the teamName given has won the game
   */
  @Override
  public boolean checkForWin(String teamName) {
    if(!board.determineIfOppositeTeamHasMove(teamName)) {
      viewController.gameWin(teamName);
      return true;
    }
    return false;

  }
}
