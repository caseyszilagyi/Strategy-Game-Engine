package ooga.model.initialization.pieces;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.ExceptionHandler;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;
import ooga.model.components.moverestrictions.Restriction;
import ooga.model.components.movetypes.PieceMovement;

public class PieceComponentClassLoader {

  private final String PIECE_MOVE_CLASSES_PACKAGE = PieceMovement.class.getPackageName();
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
   * @return The PieceMovement object
   */
  public PieceMovement makePieceMove(String moveType, Map<String, String> parameters, int direction) {
    PieceMovement move = null;
    try {
      Object command = classLoader.loadClass(PIECE_MOVE_CLASSES_PACKAGE + "." + moveType)
          .getDeclaredConstructor(Map.class, int.class, GameBoard.class, FrontEndExternalAPI.class)
          .newInstance(parameters, direction, gameBoard, viewController);
      move = (PieceMovement) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new ExceptionHandler("InvalidPieceMovementType");
    }
    return move;
  }

  //public Restriction makeRestriction(String restrictionName){

 // }


}
