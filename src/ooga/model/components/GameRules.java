package ooga.model.components;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.turnconditions.TurnCondition;
import ooga.model.engine.action_files.Action;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

public class GameRules {

  private static final String RULE_FILE_PATH = "data/gamelogic/game_rules/";
  private static final String TURN_CONDITION_FILE_PATH = "ooga.model.components.turnconditions.";
  private static final String FILE_EXTENSION = ".xml";
  private static final String TURN_CONDITION_NAME_EXTENSION = "TurnCondition";
  private static final String FILE_TYPE = "rules";
  private static final String TURN_CONDITION = "turnConditions";

  private final Map<String, List<Node>> gameFileContents;
  private final File ruleFile;

  private XMLParser xmlParser;

  public GameRules(String gameName){
    xmlParser = new XMLParser();
    ruleFile = new File(RULE_FILE_PATH + gameName + FILE_EXTENSION);
    gameFileContents = xmlParser.makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
  }


  /**
   * Gets a List of the turn conditions for the current game as a List of Strings
    * @return a List<String> of the current game's turn conditions
   */
  public List<String> getTurnConditionsAsStringList(){
    List<String> listOfTurnConditions = new ArrayList<>();
    for (Node n : gameFileContents.get(TURN_CONDITION)){
      for(String condition : n.getTextContent().split("\\s")){
        if(condition != ""){
          //System.out.printf("Condition = %s;\n", condition);
          listOfTurnConditions.add(condition);
        }
      }
    }
    return listOfTurnConditions;
  }

  /**
   * Checks the rules to see if it's the players next turn, and moves it along if it is
   */
  public boolean checkForNextTurn() {
    List<String> listOfTurnConditions = getTurnConditionsAsStringList();

    for(String condition : listOfTurnConditions){
      try{
        Class<?> clazz = Class.forName(TURN_CONDITION_FILE_PATH + condition + TURN_CONDITION_NAME_EXTENSION);
        TurnCondition turnConditionClass = (TurnCondition) clazz.getConstructor().newInstance();
        if(!turnConditionClass.isTurnOver()){
          return false;
        }
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
        System.err.println("No Condition found for condition type: " + condition);
        e.printStackTrace();
      } catch (ClassNotFoundException e){
        System.err.println("No Class found for condition type: " + condition);
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * Checks if a player has won
   */
  public boolean checkForFinish() {
    return false;
  }


}
