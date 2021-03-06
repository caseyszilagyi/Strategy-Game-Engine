package ooga.model.components;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ooga.controller.FrontEndExternalAPI;
import ooga.exceptions.ClassLoaderException;
import ooga.exceptions.XMLParseException;
import ooga.model.components.turnconditions.TurnCondition;
import ooga.model.components.turnconditions.TurnConditionResult;
import ooga.model.components.winconditions.EndGameConditioin;
import ooga.model.initialization.fileparsing.XMLParser;
import ooga.model.initialization.gameflow.ConditionClassLoader;
import org.w3c.dom.Node;

/**
 * This class defines the set of rules to be followed by a game, such as the win conditions and the turn conditions
 *
 * Example Code:
 *
 * GameBoard board = new GameBoard(8, 8);
 * DummyViewController viewController = new DummyViewController();
 * GameRules gameRules = new GameRules("testConstant", viewController, board);
 * assertFalse(gameRules.checkForNextTurn(board, basicTestPiece)); //this test should always return False, for if you check the testConstant.txt file you will see that the TurnCondition is set to Constant
 *
 * @author Cole Spector
 * @author Casey Szilagyi
 */
public class GameRules {

  private static final String RULE_FILE_PATH = "data/gamelogic/game_rules/";
  private static final String TURN_CONDITION_FILE_PATH = "ooga.model.components.turnconditions.";
  private static final String FILE_EXTENSION = ".xml";
  private static final String TURN_CONDITION_NAME_EXTENSION = "TurnCondition";
  private static final String FILE_TYPE = "rules";
  private static final String TURN_CONDITION = "turnConditions";
  private static final String WIN_CONDITION = "winConditions";
  private static final String WIN_CONDITION_FILE_PATH = EndGameConditioin.class.getPackageName() + ".";
  private static final String ADD_PIECE_TYPE = "addPieceType";
  private static final String ALL_WHITE_SPACE = "\\s";
  private static final String EMPTY_STRING = "";

  private final Map<String, List<Node>> gameFileContents;
  private final File ruleFile;
  private List<EndGameConditioin> winConditions;
  private String gameName;

  private XMLParser xmlParser;
  private ConditionClassLoader classLoader;
  private GameBoard board;
  private FrontEndExternalAPI viewController;

  /**
   * This is the initializer for the GameRules class
   *
   * This method assumes that the String gameName passed in is associated with a .xml file in the game_rules folder
   * @param gameName A string, set to the name of the game to be played
   * @param viewController the FrontEndExternalAPI being used for the game being played
   * @param board the GameBoard object being used for the game being played
   */
  public GameRules(String gameName, FrontEndExternalAPI viewController, GameBoard board){
    this.gameName = gameName;
    xmlParser = new XMLParser();
    ruleFile = new File(RULE_FILE_PATH + gameName + FILE_EXTENSION);
    gameFileContents = xmlParser.makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
    this.board = board;
    this.viewController = viewController;
    classLoader = new ConditionClassLoader(board, viewController);
    makeWinConditions();
  }

  /**
   * This method makes all of the win conditions for the current game
   */
  public void makeWinConditions(){
    winConditions = new ArrayList<>();
    if(!gameFileContents.containsKey(WIN_CONDITION)){
      throw new XMLParseException("NoWinConditions");
    }
    for (Node n : gameFileContents.get(WIN_CONDITION)){
      for(String condition : n.getTextContent().split(ALL_WHITE_SPACE)){
        if(condition != EMPTY_STRING){
          winConditions.add(makeCondition(condition));
        }
      }
    }
  }

  /**
   * Gets a List of the turn conditions for the current game as a List of Strings
   * @return a {@code List<String>} of the current game's turn conditions
   */
  public List<String> getTurnConditionsAsStringList(){
    List<String> listOfTurnConditions = new ArrayList<>();
    if(!gameFileContents.containsKey(TURN_CONDITION)){
      throw new XMLParseException("NoTurnConditions");
    }
    for (Node n : gameFileContents.get(TURN_CONDITION)){
      for(String condition : n.getTextContent().split(ALL_WHITE_SPACE)){
        if(condition != EMPTY_STRING){
          //System.out.printf("Condition = %s;\n", condition);
          listOfTurnConditions.add(condition);
        }
      }
    }
    return listOfTurnConditions;
  }

  private EndGameConditioin makeCondition(String condition){
    return classLoader.makeWinCondition(condition);
  }

  /**
   * This method checks the win conditions and returns whether or not the given team has won the game
   * @param teamName the team to check for if they have won the game
   * @return a boolean representing whether or not the given team has won the game
   */
  public boolean checkWinConditions(String teamName){
    return winConditions.stream().allMatch(winCondition -> winCondition.checkForWin(teamName));
  }



  /**
   * gets the piece type which can be added to the board for a game
   * @return the name of the piece type which can be added to the board
   */
  public String getAddPieceType(){
    if(gameFileContents.containsKey(ADD_PIECE_TYPE)){
      return gameFileContents.get(ADD_PIECE_TYPE).get(0).getTextContent();
    }
    //Todo: throw error
    System.err.println("No addPieceType defined in xml file");
    return null;
  }

  /**
   * Returns the name of the game which the rulefile is based off of
   * @return the name of the rulefile
   */
  public String getGameName(){
    return gameName;
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
      } catch (InstantiationException e) {
        throw new ClassLoaderException("NextTurnInstantiation");
      } catch (InvocationTargetException e) {
        throw new ClassLoaderException("NextTurnInvocation");
      } catch (NoSuchMethodException e) {
        throw new ClassLoaderException("NextTurnNoSuchMethod");
      } catch (IllegalAccessException e) {
        throw new ClassLoaderException("NextTurnIllegalAccess");
      } catch (ClassNotFoundException e) {
        throw new ClassLoaderException("NextTurnClassNotFound");
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
   * Sets the GameBoard to be used with this rule set
   * @param board the GameBoard object to be used.
   */
  public void setBoard(GameBoard board){
    this.board = board;
  }

}
