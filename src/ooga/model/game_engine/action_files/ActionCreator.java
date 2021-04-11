package ooga.model.game_engine.action_files;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;

public class ActionCreator {

  FrontEndExternalAPI viewController;

  public ActionCreator(FrontEndExternalAPI viewController){
    this.viewController = viewController;
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
      Class<?> actionClass = Class.forName("ooga.model.game_engine.action_files." + actionType + "Action");
      return (Action) actionClass.getConstructor(List.class, FrontEndExternalAPI.class).newInstance(actionParameters, viewController);

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
