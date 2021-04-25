package ooga.model.engine.running;

import java.util.ArrayList;
import java.util.List;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GameRules;
import ooga.model.components.Player;

/**
 * Designed to manage the changing of turns in a game. This is done based on the turn rules
 *
 * @author Casey Szilagyi
 */
public class TurnManager {

  private GameRules gameRules;
  private GameBoard gameBoard;

  private Player currentPlayerTurn;
  private List<Player> activePlayers = new ArrayList<>();
  private List<Long> playerTimes = new ArrayList<>();
  private Long playerStartTime;
  boolean isStartOfTurn = true;
  boolean noTurnRules = true;


  /**
   * If it is the start of the turn, the player's timer is started
   */
  protected void startIfBeginningTurn(){
    if (isStartOfTurn) {
      startPlayerTimer(currentPlayerTurn);
    }
  }

  /**
   * Ends the turn if it is over, and then calls a method to swap the active player
   * and stop the timer
   * @param x The x coordinate of the turn click
   * @param y The y coordinate of the turn click
   */
  protected void endTurn(int x, int y){
    //Fix end turn to not get called when a move hasn't even been made
    boolean isTurnOver = gameRules
        .checkForNextTurn(gameBoard, gameBoard.getPieceAtCoordinate(new Coordinate(x, y)));
    swapTurnIfOver(isTurnOver);
  }

  // Swaps the turns and stops timer if the player's turn is over
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
  public void swapTurn() {
    int currentPlayerIndex = activePlayers.indexOf(currentPlayerTurn);
    int nextPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
    currentPlayerTurn = activePlayers.get(nextPlayerIndex);
    gameBoard.nextTurn();
  }

  //Starts the active player's timer
  private void startPlayerTimer(Player player) {
    //TODO: possibly remove the Player parameter if we want to do it this way, but this only allows for one turn to be timed at a time.
    playerStartTime = System.currentTimeMillis();
  }

  //Ends the active player's timer
  private void stopPlayerTimer(Player player) {
    Long endTime = System.currentTimeMillis();
    if (activePlayers.contains(player)) {
      Long timeElapsed = endTime - playerStartTime;
      playerTimes.set(activePlayers.indexOf(player), timeElapsed);
    } else {
      //TODO: throw exception
    }
  }

  /**
   * Adds a player to this game
   *
   * @param player The object representing the player
   */
  public void addActiveUser(Player player) {
    activePlayers.add(player);
    playerTimes.add(Long.valueOf(0));
    setCurrentPlayerTurn(player);
  }

  /**
   * Sets the player who's turn it is
   *
   * @param player is the Player.java object to set the current turn to
   */
  protected void setCurrentPlayerTurn(Player player) {
    currentPlayerTurn = player;
  }

  /**
   * Gets the name of the player who's turn it is
   * @return A string representing the player's name
   */
  protected String getCurrentPlayerTurnName(){
    return currentPlayerTurn.getName();
  }


  /**
   * Sets the board being used to determine who's turn it is
   * @param board The board
   */
  protected void setBoard(GameBoard board){
    gameBoard = board;
  }

  /**
   * Sets whether there are turn rules
   *
   * @param turnRules True if there are none, false if there are
   */
  public void setIfTurnRules(Boolean turnRules) {
    noTurnRules = turnRules;
  }



  /**
   * Sets the rules that the game follows
   * @param rules The GameRules class representing the rules
   */
  public void setRules(GameRules rules) {
    gameRules = rules;
  }


}
