package ooga.model.components;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.turnconditions.TurnCondition;
import ooga.model.components.turnconditions.TurnConditionResult;
import ooga.model.components.winconditions.EndGameConditioin;
import ooga.model.initialization.fileparsing.XMLParser;
import ooga.model.initialization.gameflow.ConditionClassLoader;
import org.w3c.dom.Node;

public class GameRules {

  private static final String RULE_FILE_PATH = "data/gamelogic/game_rules/";
  private static final String TURN_CONDITION_FILE_PATH = "ooga.model.components.turnconditions.";
  private static final String FILE_EXTENSION = ".xml";
  private static final String TURN_CONDITION_NAME_EXTENSION = "TurnCondition";
  private static final String FILE_TYPE = "rules";
  private static final String TURN_CONDITION = "turnConditions";
  private static final String WIN_CONDITION = "winConditions";
  private static final String WIN_CONDITION_FILE_PATH = EndGameConditioin.class.getPackageName() + ".";

  private final Map<String, List<Node>> gameFileContents;
  private final File ruleFile;
  private List<EndGameConditioin> winConditions;

  private XMLParser xmlParser;
  private ConditionClassLoader classLoader;
  private GameBoard board;
  private FrontEndExternalAPI viewController;

  public GameRules(String gameName, FrontEndExternalAPI viewController, GameBoard board){
    xmlParser = new XMLParser();
    ruleFile = new File(RULE_FILE_PATH + gameName + FILE_EXTENSION);
    gameFileContents = xmlParser.makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
    this.board = board;
    this.viewController = viewController;
    classLoader = new ConditionClassLoader(board, viewController);
    makeWinConditions();
  }

  public void makeWinConditions(){
    winConditions = new ArrayList<>();
    if(gameFileContents.get(WIN_CONDITION) == null){ return;}
    for (Node n : gameFileContents.get(WIN_CONDITION)){
      for(String condition : n.getTextContent().split("\\s")){
        if(condition != ""){
          winConditions.add(makeCondition(condition));
        }
      }
    }
  }

  private EndGameConditioin makeCondition(String condition){
    return classLoader.makeWinCondition(condition);
  }

  public boolean checkWinConditions(String teamName){
    return winConditions.stream().allMatch(winCondition -> winCondition.checkForWin(teamName));
  }

  /**
   * Gets a List of the turn conditions for the current game as a List of Strings
    * @return a {@code List<String>} of the current game's turn conditions
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
  public boolean checkForNextTurn(GameBoard gameBoard, GamePiece gamePiece) {
    List<String> listOfTurnConditions = getTurnConditionsAsStringList();

    for(String condition : listOfTurnConditions){
      try{
        Class<?> clazz = Class.forName(TURN_CONDITION_FILE_PATH + condition + TURN_CONDITION_NAME_EXTENSION);
        TurnCondition turnConditionClass = (TurnCondition) clazz.getConstructor().newInstance();
        if(!turnConditionResultToBoolean(turnConditionClass.isTurnOver(gameBoard, gamePiece))){
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

  private boolean turnConditionResultToBoolean(TurnConditionResult turnConditionResult){
    switch (turnConditionResult){
      case STAY: return false;
      case SWITCH: return true;
      case PROMPT: return promptUserForResponse();
    }
    return promptUserForResponse();
  }

  private boolean promptUserForResponse(){
    //TODO: have the user respond to prompt in JavaFX not command line
    Scanner scanner = new Scanner(System.in);
    System.out.printf("Do you wish to end your turn, (Y)es or (N)o?\n\t");
    while(true){
      String response = scanner.nextLine();
      if(response.toLowerCase().charAt(0) == 'y' || response.toLowerCase().charAt(0) == 'n'){
        if(response.toLowerCase().charAt(0) == 'y'){
          return true;
        } else {
          return false;
        }
      }
    }
  }

  /**
   * Checks if a player has won
   */
  public boolean checkForFinish() {
    return false;
  }


}
