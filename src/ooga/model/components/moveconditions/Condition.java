package ooga.model.components.moveconditions;


import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * Meant to be used as a component of a piece movement object. Gives the movement a condition
 * that is executed if true.
 *
 * @author Casey Szilagyi
 */
public abstract class Condition {

  private GameBoard gameBoard;
  private FrontEndExternalAPI viewController;
  private GamePiece correspondingPiece;
  private String teamName;

  /**
   * Constructor used to hold things that many piece movement objects may need
   * @param viewController The controller used to communicate with the front end
   * @param gameBoard The board that the pieces are on.
   * @param parameters The map with parameters, if there are any
   * @param piece The piece that the restriction corresponds to
   */
  public Condition(FrontEndExternalAPI viewController, GameBoard gameBoard, Map<String, String> parameters, GamePiece piece){
    this.gameBoard = gameBoard;
    this.viewController = viewController;
    this.correspondingPiece = piece;
    this.teamName = piece.getPieceTeam();
  }


  public abstract void executeCondition(Coordinate endingCoordinates);

}
