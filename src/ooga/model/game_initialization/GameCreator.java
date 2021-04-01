package ooga.model.game_initialization;

import ooga.model.game_engine.Engine;

/**
 * each method here will pass the file name to the file parser, then the relevant
 * information to the classloader to load the class details
 */
public class GameCreator implements Creator{

  public Engine gameEngine;
  public FileParser fileParser = new GameFileParser();
  public ClassLoader classLoader = new GameClassLoader();

  /**
   * Initializes the default details of the game
   * @param gameName The name of the game
   */
  @Override
  public void initializeGame(String gameName) {
    String data = fileParser.initializeGame(gameName);
    gameEngine = classLoader.makeEngine(gameName, data);
  }

  @Override
  public void setBoardState(String boardFileName) {

  }

  @Override
  public void setGameRules(String rulesFileName) {

  }

  @Override
  public void setPlayer(String playerFileName) {

  }

  @Override
  public Engine getEngine() {
    return null;
  }
}
