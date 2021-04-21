package ooga.model.components.winconditions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

/**
 * This interface can be extended to be used for many different types of win conditions
 *
 * @author Casey Szilagyi
 */
public interface WinCondition {

  public abstract boolean checkForWin(String teamName);
}
