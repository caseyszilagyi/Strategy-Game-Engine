package ooga.model.engine;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import ooga.model.components.GameRules;
import ooga.model.components.Player;
import ooga.model.engine.action_files.Action;
import ooga.model.engine.action_files.ActionCreator;

/**
 * This is the engine used for running a game. It extends the regular engine. The engine takes care
 * of the logic of switching turns and making appropriate method calls to the board
 *
 * @author Casey Szilagyi
 */
public class GameEngine extends Engine {

  //Controller variables
  private FrontEndExternalAPI viewController;

  //Game variables
  private GameBoard curBoard;
  private GameRules curRules;
  private List<Action> priorActions = new ArrayList<>();

  //Player variables
  private Player currentPlayerTurn;
  private List<Player> activePlayers = new ArrayList<>();
  private List<Long> playerTimes = new ArrayList<>();
  private Long playerStartTime;

  //Action creator
  private ActionCreator actionCreator;

  //Logic flow variables
  boolean isStartOfTurn = true;
  boolean noTurnRules = true;

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
   * Sets the rules of the game based on the name of the game
   *
   * @param gameName the name of the game
   */
  public void setGameType(String gameName) {
    curRules = new GameRules(gameName);
  }


  /**
   * Called to process any click from the front end. Has to run through logic in order to determine
   * what method calls to make
   *
   * @param x The x position of the click
   * @param y The y position of the click
   */
  public void runTurn(int x, int y) {
    if (executeClick(x, y)) {
      return;
    }
    boolean isTurnOver = curRules
        .checkForNextTurn(curBoard, curBoard.getPieceAtCoordinate(new Coordinate(x, y)));
    swapTurnIfOver(isTurnOver);
  }

  // Executes the logic for a click based on the click position
  private boolean executeClick(int xClickPosition, int yClickPosition) {
    if (isStartOfTurn) {
      startPlayerTimer(currentPlayerTurn);
    }
    return !actOnCoordinates(xClickPosition, yClickPosition);
  }

  // Swaps the turns if the player's turn is over
  private void swapTurnIfOver(boolean isTurnOver) {
    if (isTurnOver) {
      stopPlayerTimer(currentPlayerTurn);
      swapTurn();
      isStartOfTurn = true;
    } else {
      isStartOfTurn = false;
    }
  }

  // Swaps the player's turns
  private void swapTurn() {
    int currentPlayerIndex = activePlayers.indexOf(currentPlayerTurn);
    int nextPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
    currentPlayerTurn = activePlayers.get(nextPlayerIndex);
  }

  /**
   * Sets the player who's turn it is
   *
   * @param player is the Player.java object to set the current turn to
   */
  public void setCurrentPlayerTurn(Player player) {
    currentPlayerTurn = player;
  }

  /**
   * Gets the player who's turn it is
   *
   * @return the Player.java object the current turn is set to
   */
  public Player getCurrentPlayerTurn() {
    return currentPlayerTurn;
  }


  /**
   * The method that actually decides what to do and acts on coordinates passed from the front end
   *
   * @param xClickPosition The x coordinate
   * @param yClickPosition The y coordinate
   * @return whether or not a valid move was made
   */
  @Override
  public boolean actOnCoordinates(int xClickPosition, int yClickPosition) {
    if (curBoard.getIsHeldPiece()) {
      return executePieceHoldingClick(xClickPosition, yClickPosition);
    }
    return executeNonPieceHoldingClick(xClickPosition, yClickPosition);
  }

  // Executes a click when a piece is currently being held
  private boolean executePieceHoldingClick(int xClickPosition, int yClickPosition) {
    curBoard.setIsHeldPiece(false);
    if (curBoard.isLegalMoveLocation(xClickPosition, yClickPosition)) {
      curBoard.movePiece(xClickPosition, yClickPosition);
      return true;
    } else {
      return false;
    }
  }

  // Executes a click when a piece is not currently being held
  private boolean executeNonPieceHoldingClick(int xClickPosition, int yClickPosition) {
    if (curBoard.isPieceAtCoordinate(xClickPosition, yClickPosition) && checkProperTeamTurn(
        xClickPosition, yClickPosition)) {
      curBoard.determineAllLegalMoves(xClickPosition, yClickPosition);
      curBoard.setIsHeldPiece(true);
      return true;
    } else {
      return false;
    }
  }

  private boolean checkProperTeamTurn(int x, int y) {
    return noTurnRules || curBoard.getPieceAtCoordinate(x, y).getPieceTeam()
        .equals(currentPlayerTurn.getName());
  }

  /**
   * Adds a player to this game
   *
   * @param player The object representing the player
   */
  @Override
  public void addActiveUser(Player player) {
    activePlayers.add(player);
    playerTimes.add(Long.valueOf(0));
    setCurrentPlayerTurn(player);
  }


  /**
   * Sets whether there are turn rules
   *
   * @param turnRules True if there are none, false if there are
   */
  public void setIfTurnRules(Boolean turnRules) {
    noTurnRules = turnRules;
  }


  @Override
  public void checkForFinish() {

  }

  @Override
  public void saveCurrentState(String fileName) {
    //TODO: add saving capabilities for the board and moves
  }


  @Override
  public void checkForNextTurn() {

  }

  @Override
  public void startPlayerTimer(Player player) {
    //TODO: possibly remove the Player parameter if we want to do it this way, but this only allows for one turn to be timed at a time.
    playerStartTime = System.currentTimeMillis();
  }

  @Override
  public void stopPlayerTimer(Player player) {
    Long endTime = System.currentTimeMillis();
    if (activePlayers.contains(player)) {
      Long timeElapsed = endTime - playerStartTime;
      playerTimes.set(activePlayers.indexOf(player), timeElapsed);
    } else {
      //TODO: throw exception
    }
  }


  /**
   * Sets the rules that are being used to play the game
   *
   * @param rules The rules object representing the rules
   */
  @Override
  public void setRules(GameRules rules) {
    curRules = rules;
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
  }

  /**
   * Gets the game board from the engine
   *
   * @return The game board used to hold the pieces
   */
  public GameBoard getBoard() {
    return curBoard;
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
