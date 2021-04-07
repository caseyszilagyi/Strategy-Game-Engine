package ooga.model.game_initialization.piece_initialization;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.ExceptionHandler;
import ooga.model.game_components.move_types.PieceMovement;

public class PieceComponentClassLoader {

  private final ClassLoader CLASS_LOADER;
  private final String PIECE_MOVE_CLASSES_PACKAGE = PieceMovement.class.getPackageName();

  /**
   * Instantiates the ClassLoader
   */
  public PieceComponentClassLoader() {
    CLASS_LOADER = new ClassLoader() {
    };
  }

  /**
   * Makes a piece movement object that corresponds to the given piece movement type
   *
   * @return The PieceMovement object
   */
  public PieceMovement makePieceMove(String moveType, Map<String, String> parameters, int direction) {
    PieceMovement move = null;
    try {
      Object command = CLASS_LOADER.loadClass(PIECE_MOVE_CLASSES_PACKAGE + "." + moveType)
          .getDeclaredConstructor(Map.class, int.class)
          .newInstance(parameters, direction);
      move = (PieceMovement) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      throw new ExceptionHandler("InvalidPieceMovementType" + moveType);
    }
    return move;
  }


}
