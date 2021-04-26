package ooga.model.initialization.clickexecutor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.exceptions.ClassLoaderException;
import ooga.exceptions.XMLParseException;
import ooga.model.components.turnconditions.TurnCondition;
import ooga.model.engine.running.ClickExecutor;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

/**
 * This class gets the proper ClickExecutor for a specific game
 *
 * This class assumes that within the game_rules .xml file
 * there is a <gameType>Move</gameType> or <gameType>Place</gameType> parameter.
 *
 * Example Code:
 *
 * ClickExecutorInitializer clickExecutorInitializer = new ClickExecutorInitializer();
 * ClickExecutor placeClickExecutor = clickExecutorInitializer.getProperClickExecutor("connectfour")
 * ClickExecutor moveClickExecutor = clickExecutorInitializer.getProperClickExecutor("chess")
 *
 * @author Cole Spector
 */
public class ClickExecutorInitializer {

  XMLParser xmlParser = new XMLParser();

  private static final String RULE_FILE_PATH = "data/gamelogic/game_rules/";
  private static final String FILE_EXTENSION = ".xml";
  private static final String FILE_TYPE = "rules";
  private static final String GAME_TYPE = "gameType";
  private static final String CLICK_EXECUTOR_FILE_PATH = "ooga.model.engine.running.";
  private static final String CLICK_EXECUTOR_NAME_EXTENSION = "ClickExecutor";


  /**
   * This method returns a ClickExecutor associated with the given gameName
   * This method assumes that within the game_rules  gameName.xml file
   * there is a <gameType>Move</gameType> or <gameType>Place</gameType> parameter.
   *
   * @param gameName is the name of the game to get the proper ClickExecutor for
   * @return the proper ClickExecutor for the specified game
   */
  public ClickExecutor getProperClickExecutor(String gameName) {
    File ruleFile = new File(RULE_FILE_PATH + gameName + FILE_EXTENSION);
    final Map<String, List<Node>> gameFileContents = xmlParser
        .makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
    String gameType = null;
    try{
      gameType = gameFileContents.get(GAME_TYPE).get(0).getTextContent();
    } catch(NullPointerException e){
      throw new XMLParseException("NoClickExecutor");
    }
    try {
      Class<?> clazz = Class
          .forName(CLICK_EXECUTOR_FILE_PATH + gameType + CLICK_EXECUTOR_NAME_EXTENSION);
      ClickExecutor clickExecutorClass = (ClickExecutor) clazz.getConstructor().newInstance();
      return clickExecutorClass;
    } catch (InstantiationException e) {
      throw new ClassLoaderException("ClickExecutorInstantiation");
    } catch (InvocationTargetException e) {
      throw new ClassLoaderException("ClickExecutorInvocation");
    } catch (NoSuchMethodException e) {
      throw new ClassLoaderException("ClickExecutorNoSuchMethod");
    } catch (IllegalAccessException e) {
      throw new ClassLoaderException("ClickExecutorIllegalAccess");
    } catch (ClassNotFoundException e) {
      throw new ClassLoaderException("ClickExecutorClassNotFound");
    }
  }
}

