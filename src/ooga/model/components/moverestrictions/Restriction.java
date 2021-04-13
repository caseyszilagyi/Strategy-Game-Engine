package ooga.model.components.moverestrictions;


import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

/**
 * Meant to be used as a component of a piece movement object, gives the movement some type
 * of restriction that alters how it is able to move
 *
 * @author Casey Szilagyi
 */
public abstract class Restriction {

  private GameBoard gameBoard;
  private FrontEndExternalAPI viewController;

  /**
   * Constructor used to hold things that many piece movement objects may need
   * @param viewController The controller used to communicate with the front end
   * @param gameBoard The board that the pieces are on.
   */
  public Restriction(FrontEndExternalAPI viewController, GameBoard gameBoard){
    this.gameBoard = gameBoard;
    this.viewController = viewController;
  }

  public abstract boolean checkRestriction();

}
