package ooga.model.initialization.engine;

import java.util.Arrays;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.engine.Engine;
import ooga.model.engine.GameEngine;
import ooga.model.initialization.BoardCreator;
import ooga.model.initialization.PlayerCreator;


/**
 * These methods are designed to initialize parts of the game that the engine needs to run. This
 * includes the players, board state, rules, and pieces.
 *
 * @author Casey Szilagyi
 */
public class EngineInitializer implements Initializer {

  public FrontEndExternalAPI boardController;

  public Engine gameEngine;
  public BoardCreator boardCreator;
  private PlayerCreator playerCreator = new PlayerCreator();


  public EngineInitializer(FrontEndExternalAPI newBoardController){
    boardController = newBoardController;
    gameEngine = new GameEngine(boardController);
  }

  /**
   * Initializes the default details of the game
   *
   * @param gameName The name of the game
   */
  @Override
  public void initializeGame (String gameName) {
    boardCreator = new BoardCreator(gameName, boardController);
    gameEngine.setGameType(gameName);

    gameEngine.setBoard(boardCreator.makeBoard());
  }

  @Override
  public void setBoardState(String boardFileName) {

  }

  @Override
  public void setGameRules(String rulesFileName) {

  }

  @Override
  public void addPlayers(String... playerFileNames) {
    Arrays.stream(playerFileNames)
        .forEach(s -> gameEngine.addActiveUser(playerCreator.makePlayer(s)));
  }

  @Override
  public Engine getEngine() {
    return gameEngine;
  }
}
