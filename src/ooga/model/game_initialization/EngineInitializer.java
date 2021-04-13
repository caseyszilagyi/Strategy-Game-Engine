package ooga.model.game_initialization;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_engine.Engine;
import ooga.model.game_engine.GameEngine;

/**
 * These methods are designed to initialize parts of the game that the engine needs to run.
 * This includes the players, board state, rules, and pieces.
 */
public class EngineInitializer implements Initializer {

  public FrontEndExternalAPI viewController;

  public Engine gameEngine;
  public BoardCreator boardCreator;

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
    boardCreator = new BoardCreator(gameName, viewController);
    gameEngine.setBoard(boardCreator.makeBoard());
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
    return gameEngine;
  }
}
