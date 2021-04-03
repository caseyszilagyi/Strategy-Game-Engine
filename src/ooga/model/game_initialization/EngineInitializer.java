package ooga.model.game_initialization;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_engine.Engine;
import ooga.model.game_engine.GameEngine;
import ooga.model.game_initialization.file_parsing.FileParser;
import ooga.model.game_initialization.file_parsing.GameFileParser;

/**
 * each method here will pass the file name to the file parser, then the relevant
 * information to the classloader to load the class details
 */
public class EngineInitializer implements Initializer {

  public FrontEndExternalAPI viewController;

  public Engine gameEngine;
  public FileParser fileParser = new GameFileParser();
  public Loader classLoader = new GameClassLoader();


  public EngineInitializer(FrontEndExternalAPI newViewController){
    viewController = newViewController;
    gameEngine = new GameEngine(viewController);
  }
  /**
   * Initializes the default details of the game
   * @param gameName The name of the game
   */
  @Override
  public void initializeGame(String gameName) {
    classLoader.makeRules(gameName);
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
