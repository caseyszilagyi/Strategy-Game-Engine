package ooga.model.initialization.engine;

import java.io.File;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.engine.running.Engine;
import ooga.model.engine.running.GameEngine;
import ooga.model.initialization.AICreator;
import ooga.model.initialization.BoardCreator;
import ooga.model.initialization.PlayerCreator;
import ooga.model.initialization.clickexecutor.ClickExecutorInitializer;


/**
 * These methods are designed to initialize parts of the game that the engine needs to run. This
 * includes the players, board state, rules, and pieces.
 *
 * @author Casey Szilagyi
 */
public class EngineInitializer implements Initializer {

  public FrontEndExternalAPI boardController;

  private Engine gameEngine;
  private BoardCreator boardCreator;
  private PlayerCreator playerCreator = new PlayerCreator();
  private AICreator gameAI = new AICreator();
  private final String AI_NAME = "AI";

  /**
   * This is the initializer for the EngineInitializer
   *
   * @param newBoardController the FrontEndExternalAPI to link the Engine to
   */
  public EngineInitializer(FrontEndExternalAPI newBoardController) {
    boardController = newBoardController;
    gameEngine = new GameEngine(boardController);
  }

  /**
   * Initializes the default details of the game
   *
   * @param gameName The name of the game
   */
  @Override
  public void initializeGame(String gameName) {
    ClickExecutorInitializer clickExecutorInitializer = new ClickExecutorInitializer();
    gameEngine.setClickExecutor(clickExecutorInitializer.getProperClickExecutor(gameName));
    boardCreator = new BoardCreator(gameName, boardController);
    gameEngine.setGameType(gameName);
    gameEngine.setBoard(boardCreator.makeBoard());
  }

  /**
   * This method initilizes the board based off of the boardFileName passed in
   *
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    boardCreator.initializeMaps(boardFileName);
    gameEngine.setBoard(boardCreator.makeBoard());
  }

  /**
   * Sets the board that the game is being played on
   *
   * @param boardFile The file that the board is in
   */
  @Override
  public void setBoardState(File boardFile) {
    boardCreator.initializeMaps(boardFile);
    gameEngine.setBoard(boardCreator.makeBoard());
  }


  /**
   * @param user     The string associated with the person playing on the bottom of the board
   * @param opponent The string associated with the person pplaying on the top of the board
   */
  @Override
  public void addPlayers(String user, String opponent) {
    gameEngine.addActiveUsers(playerCreator.makePlayer(user), playerCreator.makePlayer(opponent));
    if (opponent.equals(AI_NAME)) {
      addAI();
    }
  }

  // Adds the AI to the game
  private void addAI() {
    gameEngine.setAI(gameAI.makeAI());
  }

  /**
   * This method returns the gameEngine being used
   *
   * @return the GameEngine being used.
   */
  @Override
  public Engine getEngine() {
    return gameEngine;
  }
}
