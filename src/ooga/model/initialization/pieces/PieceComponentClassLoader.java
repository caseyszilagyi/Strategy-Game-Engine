package ooga.model.initialization.pieces;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.ExceptionHandler;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.moverestrictions.Restriction;
import ooga.model.components.moves.PieceMovement;

/**
 * Used to load the different components that compose a piece
 *
 * @author Casey Szilagyi
 */
public class PieceComponentClassLoader {

  private final String PIECE_MOVE_CLASSES_PACKAGE = PieceMovement.class.getPackageName();
  private final String MOVE_RESTRICTION_CLASSES_PACKAGE = Restriction.class.getPackageName();
  private ClassLoader classLoader;
  private GameBoard gameBoard;
  private FrontEndExternalAPI viewController;

  /**
   * Instantiates the ClassLoader
   */
  public PieceComponentClassLoader(GameBoard gameBoard, FrontEndExternalAPI viewController) {
    classLoader = new ClassLoader() {
    };
    this.gameBoard = gameBoard;
    this.viewController = viewController;
  }

  /**
   * Makes a piece movement object that corresponds to the given piece movement type
   *
   * @param moveType The name of the type of piece movement
   * @param parameters The parameter map that will determine the properties of the piece movement
   * @param direction The direction of the piece movement, changes for user/opponent
   * @param correspondingPiece The game piece that this move corresponds to
   * @return The PieceMovement object
   */
  public PieceMovement makePieceMove(String moveType, Map<String, String> parameters, int direction, GamePiece correspondingPiece) {
    PieceMovement move = null;
    try {
      Object command = classLoader.loadClass(PIECE_MOVE_CLASSES_PACKAGE + "." + moveType)
          .getDeclaredConstructor(Map.class, int.class, GameBoard.class, FrontEndExternalAPI.class, GamePiece.class)
          .newInstance(parameters, direction, gameBoard, viewController, correspondingPiece);
      move = (PieceMovement) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new ExceptionHandler("InvalidPieceMovementType");
    }
    return move;
  }

  /**
   * Makes a restriction object that corresponds to the given restriction type
   * @param restrictionName The name of the restriction
   * @param parameters The parameters of the restriction
   * @return The restriction object
   */
  public Restriction makeRestriction(String restrictionName, Map<String, String> parameters, GamePiece piece){
    Restriction restriction = null;
    try {
      Object command = classLoader.loadClass(MOVE_RESTRICTION_CLASSES_PACKAGE + "." + restrictionName)
          .getDeclaredConstructor(FrontEndExternalAPI.class, GameBoard.class, Map.class, GamePiece.class)
          .newInstance(viewController, gameBoard, parameters, piece);
      restriction = (Restriction) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new ExceptionHandler("InvalidRestrictionType");
    }
    return restriction;
  }


}
