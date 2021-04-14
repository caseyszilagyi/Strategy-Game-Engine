package ooga.model.components;

import java.io.File;
import java.util.List;
import java.util.Map;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

public class GameRules {

  private static final String FILE_PATH = "data/gamelogic/game_rules/";
  private static final String FILE_EXTENSION = ".xml";
  private static final String FILE_TYPE = "rule";

  private final Map<String, List<Node>> gameFileContents;
  public GameRules(String gameName){
    XMLParser xmlParser = new XMLParser();
    gameFileContents = xmlParser.makeRootNodeMap(new File(FILE_PATH + gameName + FILE_EXTENSION), FILE_TYPE, gameName);
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
