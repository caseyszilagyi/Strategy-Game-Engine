package ooga.model.engine.running;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GameRules;
import ooga.model.components.Player;
import ooga.model.components.computer.AI;
import ooga.model.engine.actions.Action;
import ooga.model.engine.actions.ActionCreator;

/**
 * This is the engine used for running a game. It extends the regular engine. The engine takes care
 * of the logic of switching turns and making appropriate method calls to the board
 *
 * @author Casey Szilagyi
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
  private List<Action> priorActions = new ArrayList<>();

  //Action creator
  private ActionCreator actionCreator;

  private boolean isAIPlaying = false;
  private AI computer;
  private boolean noTurnRules = false;
  private final String AI_NAME = "AI";

  private Map<String, String> playerNames = new HashMap<>();

  /**
   * Makes an instance of this game engine, and gives it the view controller to make calls to the
   * front end
   *
   * @param viewController The view controller linked to the front end
   */
  public GameEngine(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    actionCreator = new ActionCreator(viewController, curBoard);
  }

  /**
   * Called to process any click from the front end. Method calls to make are
   * determined in the clickExecutor class and turnManager class
   *
   * @param x The x position of the click
   * @param y The y position of the click
   */
  public void runTurn(int x, int y) {
    turnManager.startIfBeginningTurn();
    if(!clickExecutor.executeClick(x, y, getCurrentPlayerTurn())){
      return;
    }
    checkForWin();
    turnManager.endTurn(x, y);
    makeAIMove();
  }

  /**
   * Sets the AI
   * @param computer The AI that has been initialized
   */
  public void setAI(AI computer){
    this.computer = computer;
    isAIPlaying = true;
  }


  private void makeAIMove(){
    ArrayList<Coordinate> moves = new ArrayList<>();
    if(isAIPlaying && turnManager.getCurrentPlayerTurnName().equals(AI_NAME) && !isGameOver() && !noTurnRules){
      computer.determineMove(curBoard);
      computer.getMove().stream().forEach(coord->runTurn(coord.getX(), coord.getY()));
    }
  }

  public void setClickExecutor(ClickExecutor clickExecutor){
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
    turnManager.setIfNoTurnRules(turnRules);
    clickExecutor.setIfNoTurnRules(turnRules);
    noTurnRules = turnRules;
  }

  /**
   * Checks if game has been won
   * @return Boolean to determine game status
   */
  @Override
  public boolean checkForWin() {
    if(curRules.checkWinConditions(getCurrentPlayerTurn())){
      viewController.gameWin(getCurrentPlayerTurn());
      return true;
    }
    return false;
  }

  @Override
  public boolean isGameOver() {
    return curRules.checkWinConditions("user") || curRules.checkWinConditions("opponent")  ;
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
    playerNames.put(player1.getName(), "user");
    playerNames.put(player2.getName(), "opponent");
    clickExecutor.setPlayerMap(playerNames);
    curBoard.setPlayerMap(playerNames);
  }
  @Override
  public void addAI(AI ai){

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


  public void undoTurn(){
    curBoard.undoTurn();
  }

  @Override
  public void saveCurrentState(String fileName) {
    //TODO: add saving capabilities for the board and moves
  }

  /**
   * Executes an action. An action can be something that has to do with a piece
   *
   * @param action is the Action.java to perform
   */
  @Override
  public void executeAction(Action action) {
    if (action.executeAction(curBoard, curRules)) {
      priorActions.add(action);
    }
  }

  /**
   * Executes an action, given in string form
   *
   * @param action the string representation of the action
   */
  public void executeAction(String action) {
    executeAction(actionCreator.createAction(action));
  }


}
