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

}
