package ooga.model.game_engine.action_files;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;

public class ActionCreator {


  public Action createAction(String actionType, List<String> actionParameters){
    try{
      Class<?> actionClass = Class.forName("ooga.model.game_engine.action_files." + actionType + "Action");
      return (Action) actionClass.getDeclaredConstructor(GameBoard.class, GameRules.class).newInstance(actionType, actionParameters);

    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      System.err.println("No Action found for action type: " + actionType);
      e.printStackTrace();
      return null;
    }

  }

}
