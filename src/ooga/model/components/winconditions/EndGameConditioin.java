package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

/**
 * This interface can be extended to be used for many different types of win conditions
 *
 * @author Casey Szilagyi
 */
public interface EndGameConditioin {

  /**
   * This method is called to determine if someone has won the current game
   * @param teamName the team name to check to see if they won
   * @return a boolean for whether or not the teamName given has won the game
   */
  public abstract boolean checkForWin(String teamName);
}
