package ooga.model.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

public class GameRules {

  private static final String FILE_PATH = "data/gamelogic/game_rules/";
  private static final String FILE_EXTENSION = ".xml";
  private static final String FILE_TYPE = "rules";
  private static final String TURN_CONDITION = "turnConditions";

  private final Map<String, List<Node>> gameFileContents;
  private final File ruleFile;

  private XMLParser xmlParser;

  public GameRules(String gameName){
    xmlParser = new XMLParser();
    ruleFile = new File(FILE_PATH + gameName + FILE_EXTENSION);
    gameFileContents = xmlParser.makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
  }


  /**
   * Gets a List of the turn conditions for the current game as a List of Strings
    * @return a List<String> of the current game's turn conditions
   */
  public List<String> getTurnConditionsAsStringList(){
    List<String> listOfTurnConditions = new ArrayList<>();
    for (Node n : gameFileContents.get(TURN_CONDITION)){
      listOfTurnConditions.add(n.getTextContent().replaceAll("\\s", ""));
    }
    return listOfTurnConditions;
  }

  /**
   * Checks the rules to see if it's the players next turn, and moves it along if it is
   */
  public boolean checkForNextTurn() {
    return true;
  }

  /**
   * Checks if a player has won
   */
  public boolean checkForFinish() {
    return false;
  }


}
