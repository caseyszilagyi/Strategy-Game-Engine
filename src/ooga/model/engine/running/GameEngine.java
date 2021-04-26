package ooga.model.engine.running;

import java.util.HashMap;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;
import ooga.model.components.GameRules;
import ooga.model.components.player.Player;
import ooga.model.components.computer.AI;

/**
 * This is the engine used for running a game. It extends the regular engine. The engine takes care
 * of the logic of switching turns and making appropriate method calls to the board
 *
 * This class  is dependant on the FrontEndExternalAPI, Coordinate, GameBoard, GameRules, Player, and AI classes
 *
 * Example Code:
 *
 *  DummyViewController viewController = new DummyViewController();
 *  Engine gameEngine = new GameEngine(viewController)
 *  gameEngine.setGameType("chess");
 *  gameEngine.setBoard(new GameBoard(8,8));
 *  gameEngine.runTurn(1,0);
 *  gameEngine.runTurn(2,2);
 *
 *
 * @author Casey Szilagyi
 * @author Cole Spector
 */
public class GameEngine extends Engine {

  //Controller variables
  private FrontEndExternalAPI viewController;

  //Components for game flow
  private ClickExecutor clickExecutor;
  private TurnManager turnManager = new TurnManager();

  //Game variables
  private GameBoard curBoard;
  private GameRules curRules;

  private boolean isAIPlaying = false;
  private AI computer;
  private boolean noTurnRules = false;
  private final String AI_NAME = "AI";
  private final String USER_NAME = "user";
  private final String OPPONENT_NAME = "opponent";

  private Map<String, String> playerNames = new HashMap<>();

  /**
   * Makes an instance of this game engine, and gives it the view controller to make calls to the
   * front end
   *
   * @param viewController The view controller linked to the front end
   */
  public GameEngine(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }

  /**
   * Called to process any click from the front end. Method calls to make are determined in the
   * clickExecutor class and turnManager class
   *
   * @param x The x position of the click
   * @param y The y position of the click
   */
  public void runTurn(int x, int y) {
    turnManager.startIfBeginningTurn();
    if (!clickExecutor.executeClick(x, y, getCurrentPlayerTurn())) {
      return;
    }
    checkForWin();
    turnManager.endTurn(x, y);
    makeAIMove();
  }

  /**
   * Sets the active AI
   *
   * @param computer the AI to set as the active AI
   */
  public void setAI(AI computer) {
    this.computer = computer;
    isAIPlaying = true;
  }

  // Makes an AI move, if the ai is playing
  private void makeAIMove() {
    if (isAIPlaying && turnManager.getCurrentPlayerTurnName().equals(AI_NAME) && !isGameOver()
        && !noTurnRules) {
      computer.determineMove(curBoard);
      computer.getMove().stream().forEach(coord -> runTurn(coord.getX(), coord.getY()));
    }
  }

  /**
   * @param clickExecutor the ClickExecutor to be used
   */
  public void setClickExecutor(ClickExecutor clickExecutor) {
    this.clickExecutor = clickExecutor;
  }

  /**
   * Sets the rules of the game based on the name of the game
   *
   * @param gameName the name of the game
   */
  public void setGameType(String gameName) {
    curRules = new GameRules(gameName, viewController, curBoard);
    turnManager.setRules(curRules);
    clickExecutor.setGameRules(curRules);
    setIfNoTurnRules(false);
  }

  /**
   * Sets whether there are turn rules
   *
   * @param turnRules True if there are none, false if there are
   */
  @Override
  public void setIfNoTurnRules(Boolean turnRules) {
    clickExecutor.setIfNoTurnRules(turnRules);
    noTurnRules = turnRules;
  }

  /**
   * Checks if someone has won the game
   *
   * @return a boolean value representing whether or not someone has won the game
   */
  @Override
  public boolean checkForWin() {
    if (curRules.checkWinConditions(getCurrentPlayerTurn())) {
      viewController.gameWin(getCurrentPlayerTurn());
      turnManager.winGame(curRules.getGameName());
      return true;
    }
    return false;
  }

  /**
   * Checks to see if the win Conidition is hit.  This method is mostly used for testing
   *
   * @return whether or not the game is over
   */
  @Override
  public boolean isGameOver() {
    return curRules.checkWinConditions(USER_NAME) || curRules.checkWinConditions(OPPONENT_NAME);
  }

  /**
   * Sets the game board, and gives the game board the proper view controller
   *
   * @param board The board that holds the GamePiece objects
   */
  @Override
  public void setBoard(GameBoard board) {
    curBoard = board;
    curBoard.setViewController(viewController);
    clickExecutor.setBoard(curBoard);
    turnManager.setBoard(curBoard);
    curRules = new GameRules(curRules.getGameName(), viewController, board);
    board.setTurnManager(turnManager);
  }

  /**
   * Gets the game board from the engine
   *
   * @return The game board used to hold the pieces
   */
  @Override
  public GameBoard getBoard() {
    return curBoard;
  }

  /**
   * Adds a player to this game
   *
   * @param player1 The object representing the player
   */
  @Override
  public void addActiveUsers(Player player1, Player player2) {
    turnManager.addActiveUser(player2);
    turnManager.addActiveUser(player1);
    playerNames.put(player1.getFullName(), "user");
    playerNames.put(player2.getFullName(), "opponent");
    curBoard.setPlayerMap(playerNames);
  }

  /**
   * Gets the name of the player who's turn it is
   *
   * @return The string representing the player who's turn it is
   */
  @Override
  public String getCurrentPlayerTurn() {
    return playerNames.get(turnManager.getCurrentPlayerTurnName());
  }

  /**
   * Undoes the previous turn
   */
  public void undoTurn() {
    curBoard.undoTurn();
  }

}
