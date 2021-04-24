package ooga.model.initialization.clickexecutor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.model.components.turnconditions.TurnCondition;
import ooga.model.engine.running.ClickExecutor;
import ooga.model.initialization.fileparsing.XMLParser;
import org.w3c.dom.Node;

public class ClickExecutorInitializer {

  XMLParser xmlParser = new XMLParser();

  private static final String RULE_FILE_PATH = "data/gamelogic/game_rules/";
  private static final String FILE_EXTENSION = ".xml";
  private static final String FILE_TYPE = "rules";
  private static final String GAME_TYPE = "gameType";
  private static final String CLICK_EXECUTOR_FILE_PATH = "ooga.model.engine.actions.";
  private static final String CLICK_EXECUTOR_NAME_EXTENSION = "ClickExecutor";


  public ClickExecutor getProperClickExecutor(String gameName) {
    File ruleFile = new File(RULE_FILE_PATH + gameName + FILE_EXTENSION);
    final Map<String, List<Node>> gameFileContents = xmlParser
        .makeRootNodeMap(ruleFile, FILE_TYPE, gameName);
    String gameType = gameFileContents.get(GAME_TYPE).get(0).getTextContent();
    System.out.println(gameType);
    try {
      Class<?> clazz = Class
          .forName(CLICK_EXECUTOR_FILE_PATH + gameType + CLICK_EXECUTOR_NAME_EXTENSION);
      ClickExecutor clickExecutorClass = (ClickExecutor) clazz.getConstructor().newInstance();
      return clickExecutorClass;
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.err.println("No Condition found for condition type: " + gameType);
      e.printStackTrace();
      return null;
    } catch (ClassNotFoundException e) {
      System.err.println("No Class found for condition type: " + gameType);
      e.printStackTrace();
      return null;
    }
  }
}

