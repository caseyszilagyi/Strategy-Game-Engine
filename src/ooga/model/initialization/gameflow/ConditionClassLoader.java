package ooga.model.initialization.gameflow;

import java.lang.reflect.InvocationTargetException;
import ooga.controller.FrontEndExternalAPI;
import ooga.exceptions.ClassLoaderException;
import ooga.model.components.GameBoard;
import ooga.model.components.winconditions.EndGameConditioin;

public class ConditionClassLoader {

  private final String WIN_CONDITION_CLASSES_PACKAGE = EndGameConditioin.class.getPackageName() + ".";
  private ClassLoader classLoader;
  private GameBoard gameBoard;
  FrontEndExternalAPI viewController;

  /**
   * Instantiates the ClassLoader
   */
  public ConditionClassLoader(GameBoard gameBoard, FrontEndExternalAPI viewController) {
    classLoader = new ClassLoader() {
    };
    this.gameBoard = gameBoard;
    this.viewController = viewController;
  }

  /**
   * Makes a piece movement object that corresponds to the given piece movement type
   *
   * @param conditionName The name of the win condition
   * @return The corresponding win game condition class
   */
  public EndGameConditioin makeWinCondition(String conditionName) {
    EndGameConditioin condition = null;
    try {
      Object conditionObj = classLoader.loadClass(WIN_CONDITION_CLASSES_PACKAGE + conditionName)
          .getDeclaredConstructor(FrontEndExternalAPI.class, GameBoard.class)
          .newInstance(viewController, gameBoard);
      condition = (EndGameConditioin) conditionObj;
    } catch (InstantiationException e) {
      throw new ClassLoaderException("WinConditionInstantiation");
    } catch (InvocationTargetException e) {
      throw new ClassLoaderException("WinConditionInvocation");
    } catch (NoSuchMethodException e) {
      throw new ClassLoaderException("WinConditionNoSuchMethod");
    } catch (IllegalAccessException e) {
      throw new ClassLoaderException("WinConditionIllegalAccess");
    } catch (ClassNotFoundException e) {
      throw new ClassLoaderException("WinConditionClassNotFound");
    }
    return condition;
  }
}
