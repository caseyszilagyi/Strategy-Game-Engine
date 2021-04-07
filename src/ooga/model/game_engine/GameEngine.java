package ooga.model.game_engine;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.game_components.GameBoard;
import ooga.model.game_components.GameRules;
import ooga.model.game_components.Player;

public class GameEngine implements Engine {

  FrontEndExternalAPI viewController;

  //Game variables
  private GameBoard curBoard;
  private GameRules curRules;

  //Player variables
  private List<Player> activePlayers;
  private List<Long> playerTimes;
  private Long playerStartTime;

  public GameEngine(FrontEndExternalAPI newViewController){
    viewController = newViewController;
    activePlayers = new ArrayList<Player>();
  }

  @Override
  public void checkForFinish() {

  }

  @Override
  public void saveCurrentState(String fileName) {

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
    if(activePlayers.contains(player)){
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

  @Override
  public void setBoard(GameBoard board) {
    curBoard = board;
  }

  /**
   *  Assumes the move is in the format - Piece:ActionType:EndCoordinate where:
   *  ActionType is the type of action to take, i.e. MOVE or PLACE
   *  Piece is the piece for the action to be performed upon
   *  EndCoordinate is the coordinate Piece will end up at after the action is performed
   * @param moveSpecifications is a String, which dictates the move to be performed on the board.
   */
  @Override
  public void makeAction(String moveSpecifications) {

  }

}
