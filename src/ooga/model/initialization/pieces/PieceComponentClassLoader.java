package ooga.model.initialization.pieces;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import ooga.exceptions.ClassLoaderException;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.moveconditions.Condition;
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
  private final String MOVE_CONDITION_CLASSES_PACKAGE = Condition.class.getPackageName();
  private ClassLoader classLoader;
  private GameBoard gameBoard;

  /**
   * Instantiates the ClassLoader
   */
  public PieceComponentClassLoader(GameBoard gameBoard) {
    classLoader = new ClassLoader() {
    };
    this.gameBoard = gameBoard;
  }

  /**
   * Makes a piece movement object that corresponds to the given piece movement type
   *
   * @param moveType           The name of the type of piece movement
   * @param parameters         The parameter map that will determine the properties of the piece
   *                           movement
   * @param direction          The direction of the piece movement, changes for user/opponent
   * @param correspondingPiece The game piece that this move corresponds to
   * @return The PieceMovement object
   */
  public PieceMovement makePieceMove(String moveType, Map<String, String> parameters, int direction,
      GamePiece correspondingPiece) {
    PieceMovement move = null;
    try {
      Object command = classLoader.loadClass(PIECE_MOVE_CLASSES_PACKAGE + "." + moveType)
          .getDeclaredConstructor(Map.class, int.class, GameBoard.class, GamePiece.class)
          .newInstance(parameters, direction, gameBoard, correspondingPiece);
      move = (PieceMovement) command;
    } catch (InstantiationException e) {
      throw new ClassLoaderException("PieceMovementInstantiation");
    } catch (InvocationTargetException e) {
      throw new ClassLoaderException("PieceMovementInvocation");
    } catch (NoSuchMethodException e) {
      throw new ClassLoaderException("PieceMovementNoSuchMethod");
    } catch (IllegalAccessException e) {
      throw new ClassLoaderException("PieceMovementIllegalAccess");
    } catch (ClassNotFoundException e) {
      throw new ClassLoaderException("PieceMovementClassNotFound");
    }
    return move;
  }

  /**
   * Makes a restriction object that corresponds to the given restriction type
   *
   * @param restrictionName The name of the restriction
   * @param parameters      The parameters of the restriction
   * @param piece           The piece that the movement object corresponds to for these
   *                        restrictions
   * @return The restriction object
   */
  public Restriction makeRestriction(String restrictionName, Map<String, String> parameters,
      GamePiece piece) {
    Restriction restriction = null;
    try {
      Object command = classLoader
          .loadClass(MOVE_RESTRICTION_CLASSES_PACKAGE + "." + restrictionName)
          .getDeclaredConstructor(GameBoard.class, Map.class, GamePiece.class)
          .newInstance(gameBoard, parameters, piece);
      restriction = (Restriction) command;
    } catch (InstantiationException e) {
      throw new ClassLoaderException("RestrictionInstantiation");
    } catch (InvocationTargetException e) {
      throw new ClassLoaderException("RestrictionInvocation");
    } catch (NoSuchMethodException e) {
      throw new ClassLoaderException("RestrictionNoSuchMethod");
    } catch (IllegalAccessException e) {
      throw new ClassLoaderException("RestrictionIllegalAccess");
    } catch (ClassNotFoundException e) {
      throw new ClassLoaderException("RestrictionClassNotFound");
    }
    return restriction;
  }

  /**
   * Makes a condition object that corresponds to the given condition type
   *
   * @param conditionName The name of the condition
   * @param parameters    The parameters of the condition
   * @param piece         The piece that the condition corresponds to
   * @return The condition object
   */
  public Condition makeCondition(String conditionName, Map<String, String> parameters,
      GamePiece piece, int direction) {
    Condition condition = null;
    try {
      Object command = classLoader.loadClass(MOVE_CONDITION_CLASSES_PACKAGE + "." + conditionName)
          .getDeclaredConstructor(GameBoard.class, Map.class, GamePiece.class, int.class)
          .newInstance(gameBoard, parameters, piece, direction);
      condition = (Condition) command;
    } catch (InstantiationException e) {
      throw new ClassLoaderException("ConditionInstantiation");
    } catch (InvocationTargetException e) {
      throw new ClassLoaderException("ConditionInvocation");
    } catch (NoSuchMethodException e) {
      throw new ClassLoaderException("ConditionNoSuchMethod");
    } catch (IllegalAccessException e) {
      throw new ClassLoaderException("ConditionIllegalAccess");
    } catch (ClassNotFoundException e) {
      throw new ClassLoaderException("ConditionClassNotFound");
    }
    return condition;
  }


}
