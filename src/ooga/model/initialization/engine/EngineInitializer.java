package ooga.model.initialization.engine;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.engine.running.ClickExecutor;
import ooga.model.components.computer.AI;
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

  public Engine gameEngine;
  public BoardCreator boardCreator;
  private PlayerCreator playerCreator = new PlayerCreator();
  private AICreator gameAI = new AICreator();
  private final String AI_NAME = "AI";

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

    ClickExecutorInitializer clickExecutorInitializer = new ClickExecutorInitializer();
    gameEngine.setClickExecutor(clickExecutorInitializer.getProperClickExecutor(gameName));
    boardCreator = new BoardCreator(gameName, boardController);
    gameEngine.setGameType(gameName);
    gameEngine.setBoard(boardCreator.makeBoard());


  }

  /**
   * Sets the board state from a file
   * @param boardFileName The file name that contains the board
   */
  @Override
  public void setBoardState(String boardFileName) {
    boardCreator.initializeMaps(boardFileName);
    gameEngine.setBoard(boardCreator.makeBoard());
  }

  @Override
  public void setGameRules(String rulesFileName) {

  }

  /**
   * Adds the players, if there's only one player and an AI, adds the AI as well
   * @param user The string associated with the person playing on the bottom of the board
   * @param opponent The string associated with the person playing on the top of the board
   */
  @Override
  public void addPlayers(String user, String opponent) {
    gameEngine.addActiveUsers(playerCreator.makePlayer(user), playerCreator.makePlayer(opponent));
    //boardCreator.setTeams(user, opponent);
    if(opponent.equals(AI_NAME)){
      addAI();
    }
  }

  /**
   * Makes the AI
   */
  @Override
  public void addAI(){
    gameEngine.setAI(gameAI.makeAI());
  }
  @Override
  public Engine getEngine() {
    return gameEngine;
  }
}
