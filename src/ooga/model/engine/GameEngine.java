package ooga.model.engine;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.GameBoard;
import ooga.model.components.GameRules;
import ooga.model.components.Player;
import ooga.model.engine.action_files.Action;
import ooga.model.engine.action_files.ActionCreator;

public class GameEngine extends Engine {


  //Final index values
  private static final int ACTION_TYPE_INDEX = 0;
  private static final int STARTING_COORDINATE_INDEX = 1;
  private static final int ENDING_COORDINATE_INDEX = 2;

  //Controller variables
  private FrontEndExternalAPI viewController;

  //Game variables
  private GameBoard curBoard;
  private GameRules curRules;
  private List<Action> priorActions;

  //Player variables
  private Player currentPlayerTurn;
  private List<Player> activePlayers;
  private List<Long> playerTimes;
  private Long playerStartTime;

  //Action creator
  private ActionCreator actionCreator;

  //Logic flow variables
  boolean pieceSelected = false;

  public GameEngine(FrontEndExternalAPI newViewController) {
    viewController = newViewController;
    activePlayers = new ArrayList<>();
    priorActions = new ArrayList<>();
    actionCreator = new ActionCreator(viewController, curBoard);
  }

  /**
   * This is the method that is called to run the current turn
   * must set a currentPlayerTurn before calling
   * Todo: make sure that a currentPlayerTurn has been set
   */
  public void runTurn(){
    if(currentPlayerTurn == null){
      System.err.println("No player turn set");
      return;
    }
    startPlayerTimer(currentPlayerTurn);
    boolean isTurnOver = true;
    do {

    } while (!isTurnOver);
  }

  /**
   * Sets the player who's turn it is
   *
   * @param player is the Player.java object to set the current turn to
   */
  public void setCurrentPlayerTurn(Player player){

  }


  /**
   * The method that actually decides what to do and acts on coordinates passed from the front end
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  @Override
  public void actOnCoordinates(int x, int y) {
    // This logic is for move games, only works for chess and not for double jumps in checkers
    if(pieceSelected){
      if(curBoard.isLegalMoveLocation(x, y)){
        curBoard.movePiece(x,y);
      }
      pieceSelected = false;
    }
    else {
      if (curBoard.isPieceAtCoordinate(x, y)) {
        curBoard.determineAllLegalMoves(x, y);
        pieceSelected = true;
      }
    }
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


  @Override
  public void checkForFinish() {

  }

  @Override
  public void saveCurrentState(String fileName) {
    //TODO: add saving capabilities for the board and moves
  }

  @Override
  public void addActiveUser(Player player) {
    activePlayers.add(player);
    playerTimes.add(Long.valueOf(0));
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


  @Override
  public void setRules(GameRules rules) {
    curRules = rules;
  }

  /**
   * Sets the game board, and gives the game board the proper view controller
   * @param board The board that holds the GamePiece objects
   */
  @Override
  public void setBoard(GameBoard board) {
    curBoard = board;
    curBoard.setViewController(viewController);
  }


  // for testing
  public GameBoard getBoard() {
    return curBoard;
  }


}
