package ooga.model.engine.action_files;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;

public class ActionCreator {

  private static final String FILE_PATH = "ooga.model.engine.action_files.";
  private static final String NAME_EXTENSION = "Action";

  private FrontEndExternalAPI viewController;
  private GameBoard gameBoard;

  public ActionCreator(FrontEndExternalAPI viewController, GameBoard gameBoard){
    this.viewController = viewController;
    this.gameBoard = gameBoard;
  }

  /**
   * Spaces between actions
   * i.e "Move 2:2 3:3"
   *
   * @param actionString The string representation the action
   * @return The action represented by the string
   */
  public Action createAction(String actionString){
    String[] actionSplit = actionString.split(" ");
    List<String> params = new ArrayList<String>(Arrays.asList(actionSplit));
    String type = params.get(0);
    params.remove(0);
    return createAction(type, params);
  }

  public Action createAction(String actionType, List<String> actionParameters){
    try{
      Class<?> actionClass = Class.forName(FILE_PATH + actionType + NAME_EXTENSION);
      return (Action) actionClass.getConstructor(List.class, FrontEndExternalAPI.class, GameBoard.class).newInstance(actionParameters, viewController, gameBoard);

    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.err.println("No Action found for action type: " + actionType);
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      System.err.println("No Class found for action type: " + actionType);
      e.printStackTrace();
    }
    return null;

  }

}
