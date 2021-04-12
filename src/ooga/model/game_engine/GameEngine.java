package ooga.model.game_engine;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.Coordinate;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GamePiece;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;
import ooga.model.game_engine.action_files.Action;
import ooga.model.game_engine.action_files.ActionCreator;

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
  private List<Player> activePlayers;
  private List<Long> playerTimes;
  private Long playerStartTime;

  //Action creator
  private ActionCreator actionCreator;

  public GameEngine(FrontEndExternalAPI newViewController) {
    viewController = newViewController;
    activePlayers = new ArrayList<>();
    priorActions = new ArrayList<>();
    actionCreator = new ActionCreator(viewController, curBoard);
  }


  /**
   * The method that actually decides what to do and acts on coordinates passed fromt the front end
   *
   * @param x The x coordinate
   * @param y The y coordinate
   */
  @Override
  public void actOnCoordinates(int x, int y) {
    // TODO: Add logic that determines what method is called on the game board depending on the game type
    curBoard.determineAllLegalMoves(x, y);
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
